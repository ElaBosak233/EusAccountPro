package cn.elabosak.eusaccountpro;

import cn.elabosak.eusaccountpro.controller.AuthController;
import cn.elabosak.eusaccountpro.database.Database;
import cn.elabosak.eusaccountpro.database.JsonDB;
import cn.elabosak.eusaccountpro.database.MySQL;
import cn.elabosak.eusaccountpro.database.SQLite;
import cn.elabosak.eusaccountpro.exception.NotRegistered;
import cn.elabosak.eusaccountpro.utils.Authenticator;
import com.google.zxing.WriterException;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


public final class EusAccountPro extends JavaPlugin implements Listener{

    public HashMap<Player, ItemStack[]> oldInvs = new HashMap<Player, ItemStack[]>();
    public HashMap<Player, Boolean> loggedIn = new HashMap<>();
    public HashMap<Player, Boolean> verify = new HashMap<>();
    public HashMap<Player, Location> odloc = new HashMap<>();
    public HashMap<Player, GameMode> odgmode = new HashMap<>();
    public HashMap<Player, Boolean> isCreating = new HashMap<>();

    AuthController authController;
    Database database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(ChatColor.BOLD + "+++ EusAccountPro Online √ +++");
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
        }
        reloadConfig();

        new EAPConfig(this);

        switch (EAPConfig.dbType) {
            case SQLite:
                // TODO SQLite support
                database = new SQLite();
                break;
            case MySQL:
                // TODO MySQL support
                database = new MySQL();
                break;
            case JSON:
                database = new JsonDB();
            default:
                database = new JsonDB();
        }
        authController = new AuthController(this);
        getServer().getPluginManager().registerEvents(this,this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.BOLD + "--- EusAccountPro Offline × ---");
    }

    // TODO 这里要写一个监听器，监听玩家在线、离线
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        verify.put(event.getPlayer(),true); // 默认设置verify为true，免得有人找茬来验证
        if(getDatabase().isPlayerRegistered(event.getPlayer().getUniqueId())){
            loggedIn.put(event.getPlayer(),false);
            isCreating.put(event.getPlayer(),false);
            event.getPlayer().sendMessage(ChatColor.GREEN+"§l+ EusAccountPro 正在保护你的账户 +");
            Location odLoc = event.getPlayer().getLocation();
            odloc.put(event.getPlayer(),odLoc);
            Location loc = getDatabase().getSafePoint(event.getPlayer().getUniqueId());
            odgmode.put(event.getPlayer(),event.getPlayer().getGameMode());
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
            event.getPlayer().teleport(loc);
            oldInvs.put(event.getPlayer(),event.getPlayer().getInventory().getContents());
            event.getPlayer().getInventory().clear();
            event.getPlayer().sendMessage(ChatColor.GREEN+"使用 /2fa <code> 进行验证");
        }else{
            event.getPlayer().sendMessage(ChatColor.BLUE+"§lEAP -> EusAccountPro 已推出 -");
            event.getPlayer().sendMessage(ChatColor.GREEN+"§lEAP -> 使用 /eap create 创建二步验证 -");
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("eap")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length >= 1){
                    if (args[0].equalsIgnoreCase("create")) {
                        //输入eap create，即判断后创建2fa
                        UUID uuid = p.getUniqueId();
                            if(args.length == 1){
                                try {
                                    if(getDatabase().getSafePoint(uuid) != null){
                                        if(isCreating.get(p)){
                                            p.sendMessage(ChatColor.RED+"§l你正在创建EAP");
                                        }else{
                                            isCreating.put(p,true);
                                            p.sendMessage(ChatColor.GREEN + "§l+ EAP -> " + ChatColor.GOLD + "正在创建二步验证QRCode...");
                                            oldInvs.put(p, p.getInventory().getContents());
                                            p.sendMessage(ChatColor.GREEN + "§l+ EAP -> " + ChatColor.GOLD + "物品栏已保存...");
                                            p.getInventory().clear();
                                            String secretKey = Authenticator.generateSecretKey(); //生成SecretKey
                                            try {
                                                authController.register(p,secretKey); //注册
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                                p.sendMessage(ChatColor.RED+"程序异常，进行authController.register()异常");
                                                return true;
                                            }
                                            String QRCode_url = Authenticator.getGoogleAuthenticatorQRCode(secretKey, p.getName() , getConfig().getString("Account.Display"));
                                            try {
                                                Authenticator.createQRCode(QRCode_url, "plugins/EusAccountPro/QRCode/",uuid.toString()+".png", 128 ,128);
                                            } catch (WriterException | IOException e) {
                                                e.printStackTrace();
                                                p.sendMessage(ChatColor.RED+"程序异常，进行createQRCode()异常");
                                                return true;
                                            }
                                            ItemStack map = new ItemStack(Material.FILLED_MAP);
                                            MapView view = getServer().createMap(getServer().getWorlds().get(0));
                                            for(MapRenderer renderer : view.getRenderers())
                                                view.removeRenderer(renderer);
                                            view.addRenderer(new MapRenderer() {
                                                @Override
                                                public void render(MapView map, MapCanvas canvas, Player player) {
                                                    canvas.drawImage(0,0,new ImageIcon("plugins/EusAccountPro/QRCode/"+player.getUniqueId().toString()+".png").getImage());
                                                }
                                            });
                                            MapMeta mapMeta = ((MapMeta)map.getItemMeta());
                                            mapMeta.setMapView(view);
                                            map.setItemMeta(mapMeta);
                                            p.getInventory().setItem(4,map);
                                            p.getInventory().setHeldItemSlot(4);
                                            verify.put(p,false);
                                            p.sendMessage(ChatColor.GREEN+"请扫描二维码，并使用 /eap verify <code> 进行初始验证");
                                            p.sendMessage(ChatColor.GOLD+"若无法扫描二维码，请输入以下密钥 "+getDatabase().getSecretKey(uuid));
                                            return true;
                                        }
                                    }else{
                                        p.sendMessage(ChatColor.RED+"尚未设置安全点，请在安全的地方运行"+ChatColor.GREEN+"§l /eap safepoint");
                                        return true;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    p.sendMessage(ChatColor.RED+"程序异常，进行getSafePoint()异常");
                                    return true;
                                }
                            }else{
                                p.sendMessage(ChatColor.RED+"数据过量，请使用 /eap creat");
                            }

                    } else {
                        if (args[0].equalsIgnoreCase("delete")) {
                            //输入eap delete，即判断后删除2fa
                            if(!loggedIn.get(p)){
                                p.sendMessage(ChatColor.RED+"§l请先认证");
                                return true;
                            }else{
                                if(getDatabase().isPlayerRegistered(p.getUniqueId())){
                                    if (getDatabase().deletePlayer(p.getUniqueId())){
                                        p.sendMessage(ChatColor.GREEN + "§l删除成功");
                                        return true;
                                    }else{
                                        p.sendMessage(ChatColor.RED + "§l删除失败");
                                        return true;
                                    }
                                }else{
                                    p.sendMessage(ChatColor.RED+"§l尚未注册");
                                    return true;
                                }
                            }
                        } else {
                            if (args[0].equalsIgnoreCase("safepoint")) {
                                //输入eap safepoint，当玩家激活了2fa后，需要验证2fa的时候，自动传送到这个坐标，以免遭遇伤害
                                Location safepoint = p.getLocation();
                                UUID uuid = p.getUniqueId();
                                try {
                                    if(getDatabase().SafePoint(uuid, safepoint)){
                                        p.sendMessage(ChatColor.GREEN+"§l安全点已记录");
                                        return true;
                                    }else{
                                        p.sendMessage(ChatColor.RED+"§l安全点记录失败");
                                        return true;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                if (args[0].equalsIgnoreCase("verify")){
                                    if(!verify.get(p)){
                                        if(args.length != 2){
                                            p.sendMessage(ChatColor.RED+"请提供动态密码");
                                            return true;
                                        }else{
                                            try {
                                                if(authController.verify(p,args[1])){
                                                    verify.put(p,true);
                                                    p.sendMessage(ChatColor.GREEN+"§l初始化验证成功");
                                                    p.getInventory().setContents(oldInvs.get(p));
                                                    return true;
                                                }else{
                                                    p.sendMessage(ChatColor.RED+"§l动态密码无效，验证失败");
                                                    return true;
                                                }
                                            } catch (NotRegistered | IOException notRegistered) {
                                                notRegistered.printStackTrace();
                                            }
                                        }
                                    }else{
                                        p.sendMessage(ChatColor.RED+"你不需要验证");
                                        return true;
                                    }
                                } else {
                                    //指令错误，显示使用帮助
                                    p.sendMessage(ChatColor.RED+"§l+++++ EusAccountPro +++++");
                                    p.sendMessage(ChatColor.GREEN+"/eap safepoint 记录玩家安全点");
                                    p.sendMessage(ChatColor.GREEN+"/eap create 注册EAP");
                                    p.sendMessage(ChatColor.GREEN+"/eap delete 注销EAP");
                                    p.sendMessage(ChatColor.GREEN+"/eap verify <code> 初始化二步验证");
                                    p.sendMessage(ChatColor.GREEN+"/2fa <code> 进服二步验证");
                                    p.sendMessage(ChatColor.BLUE+"/eapre <玩家名> 强制删除二步验证 (需要管理员权限)");
                                    p.sendMessage(ChatColor.RED+"§l----- EusAccountPro -----");
                                    return true;
                                }
                            }
                        }
                    }
                }else{
                    //仅输入eap，显示使用帮助
                    p.sendMessage(ChatColor.RED+"§l+++++ EusAccountPro +++++");
                    p.sendMessage(ChatColor.GREEN+"/eap safepoint 记录玩家安全点");
                    p.sendMessage(ChatColor.GREEN+"/eap create 注册EAP");
                    p.sendMessage(ChatColor.GREEN+"/eap delete 注销EAP");
                    p.sendMessage(ChatColor.GREEN+"/eap verify <code> 初始化二步验证");
                    p.sendMessage(ChatColor.GREEN+"/2fa <code> 进服二步验证");
                    p.sendMessage(ChatColor.BLUE+"/eapre <玩家名> 强制删除二步验证 (需要管理员权限)");
                    p.sendMessage(ChatColor.RED+"§l----- EusAccountPro -----");
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.BOLD + "你必须作为一个玩家执行此命令");
                return true;
            }
        }
        if (command.getName().equalsIgnoreCase("2fa")) {
            if (sender instanceof Player) {
                //获取用户输入
                Player p = (Player) sender;
                if (args.length != 1) {
                    //什么都没有，显示使用方法
                    sender.sendMessage(ChatColor.RED+"§l数据异常，请输入 /2fa <code>");
                    return true;
                } else {
                    //待加入：先行判断，1.该玩家是否已激活2fa 2.该玩家是否已经验证过2fa
                    if (loggedIn.getOrDefault(p, false)) {
                        p.sendMessage(ChatColor.AQUA + "您已认证");
                        return true;
                    }
                    try {
                        if (authController.verify(p, args[0])) {
                            // Success
                            loggedIn.put(p, true);
                            p.sendMessage(ChatColor.GREEN + "认证成功");
                            p.getInventory().setContents(oldInvs.get(p));
                            p.setGameMode(odgmode.get(p));
                            p.teleport(odloc.get(p));
                            return true;
                        } else {
                            // Invalid code
                            p.sendMessage(ChatColor.YELLOW + "认证失败");
                            return true;
                        }
                    } catch (NotRegistered | IOException e) {
                        p.sendMessage(ChatColor.RED + "尚未注册或程序异常");
                        return true;
                    }
                }
            } else {
                sender.sendMessage(ChatColor.BOLD + "你必须作为一个玩家执行此命令");
                return true;
            }
        }
        if (command.getName().equalsIgnoreCase("eapre")) {
            if (sender instanceof Player){
                if (sender.hasPermission("EusAccountPro.admin")) {
                    if (args.length != 1) {
                        sender.sendMessage(ChatColor.RED+"§l目标缺失，请输入 /eapre [玩家名]");
                        return true;
                    } else {
                        Player target = Bukkit.getPlayer(args[0]); //定义此为该玩家的名称，接下来验证是否已激活2fa，若为是，则删除其记录
                        UUID uuid = target.getUniqueId();
                        if(getDatabase().isPlayerRegistered(uuid)){
                            if (getDatabase().deletePlayer(uuid)){
                                sender.sendMessage(ChatColor.GREEN+"§l删除成功");
                                return true;
                            }else{
                                sender.sendMessage(ChatColor.GREEN+"§l删除失败");
                                return true;
                            }
                        }else{
                            sender.sendMessage(ChatColor.RED + "§l该玩家尚未注册");
                            return true;
                        }

                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "§l你没有使用此命令的权限");
                }
            }else{
                sender.sendMessage(ChatColor.RED + "§l你必须作为一个玩家执行此命令");
                return true;
            }
        }
        return false;
    }

    public Database getDatabase() {
        return database;
    }
}
