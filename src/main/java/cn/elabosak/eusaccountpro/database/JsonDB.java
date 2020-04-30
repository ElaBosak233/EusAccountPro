package cn.elabosak.eusaccountpro.database;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.elabosak.eusaccountpro.utils.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import cn.elabosak.eusaccountpro.EusAccountPro;
import cn.elabosak.eusaccountpro.utils.str2loc;

public class JsonDB extends Database {

    EusAccountPro plugin;

    @Override
    public String getSecretKey(UUID uuid) {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
<<<<<<< HEAD
<<<<<<< HEAD
        String Filepath = "plugins/EusAccountPro/JsonDB/SecretKeys/"+uuid_string+".json";
//        String Filepath = plugin.getDataFolder()+"/JsonDB/SecretKeys/"+uuid_string+".json";
=======
        String Filepath = "plugins/EusAccountPro/JsonDB/Players/"+uuid_string+".json";
>>>>>>> parent of e751e49... eap exit and inv json done
=======
        String Filepath = "plugins/EusAccountPro/JsonDB/Players/"+uuid_string+".json";
>>>>>>> parent of e751e49... eap exit and inv json done
        File file = new File(Filepath);
        if(!file.exists()){
            return null; //文件不存在，返回null
        }else{
            String js = FileUtil.ReadFile(Filepath);
            JSONObject jsonObject = JSON.parseObject(js);
            return jsonObject.getString("secretKey");
        }
    }

    @Override
    public boolean updatePlayer(UUID uuid, String secretKey) throws IOException {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        Map<String,String> data = new HashMap<String,String>();
        data.put("secretKey",secretKey);
        Object mapJson = JSONObject.toJSON(data);
        File mkdirs = new File("plugins/EusAccountPro/JsonDB/Players/");
        if(!mkdirs.exists()){
            mkdirs.mkdirs();
        }
        String format = ".json";
        String file_name = uuid_string + format;
        File file = new File("plugins/EusAccountPro/JsonDB/Players//"+file_name);
        if(!file.exists()){
            file.createNewFile();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            JSONObject.writeJSONString(fileOutputStream,mapJson);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean isPlayerRegistered(UUID uuid) {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        File file = new File("plugins/EusAccountPro/JsonDB/Players/"+uuid_string+".json");
        return file.exists(); //文件不存在，返回false
    }

    @Override
    public boolean deletePlayer(UUID uuid) {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        File file = new File("plugins/EusAccountPro/JsonDB/Players/"+uuid_string+".json");
        File QRCode = new File("plugins/EusAccountPro/QRCode/"+uuid_string+".png");
        if(!file.exists() && !QRCode.exists()){
            return false; //文件不存在，返回false
        }else{
            return file.delete() && QRCode.delete();
        }
    }

    @Override
    public boolean SafePoint(UUID uuid, Location safepoint) throws IOException {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        Map<String,String> loc = new HashMap<String,String>();
        loc.put("safepoint",str2loc.loc2str(safepoint));
        plugin.getServer().getConsoleSender().sendMessage("调试信息：位置已转换");
        Object locJson = JSONObject.toJSON(loc);
        File mkdirs = new File("plugins/EusAccountPro/JsonDB/SafePoint/");
        if(!mkdirs.exists()){
            mkdirs.mkdirs();
            plugin.getServer().getConsoleSender().sendMessage("调试信息：文件夹已经创建");
        }
        String format = ".json";
        String file_name = uuid_string + format;
        File file = new File("plugins/EusAccountPro/JsonDB/SafePoint//"+file_name);
        if(!file.exists()){
            file.createNewFile();
            plugin.getServer().getConsoleSender().sendMessage("调试信息：文件已经创建");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            JSONObject.writeJSONString(fileOutputStream,locJson);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Location getSafePoint(UUID uuid) {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        String Filepath = "plugins/EusAccountPro/JsonDB/SafePoint/"+uuid_string+".json";
        File file = new File(Filepath);
        if(!file.exists()){
            return null; //文件不存在，返回null
        }else{
            String js = FileUtil.ReadFile(Filepath);
            JSONObject jsonObject = JSON.parseObject(js);
            Bukkit.getServer().getConsoleSender().sendMessage("调试信息：文件已转换");
            String safepoint_json = jsonObject.getString("safepoint");
            Bukkit.getServer().getConsoleSender().sendMessage("调试信息：安全点已获取");
            Bukkit.getServer().getConsoleSender().sendMessage("调试信息：安全点位置于 "+safepoint_json);
            if (safepoint_json != null){
                return str2loc.str2loc(safepoint_json);

            }else{
                return null;
            }
        }
    }

<<<<<<< HEAD
<<<<<<< HEAD
    @Override
    public boolean updateInv(UUID uuid, Inventory inventory) throws IOException {
        String uuid_string = uuid.toString();
        String mkdirs = "plugins/EusAccountPro/JsonDB/Invs/";
        File mkdir = new File(mkdirs);
        if (!mkdir.exists()){
            return mkdir.mkdirs();
        }
        String format = ".json";
        String file_name = uuid_string + format;
        File file = new File("plugins/EusAccountPro/JsonDB/Invs//"+ file_name);
        Map<String,Inventory> inv = new HashMap<String,Inventory>();
        inv.put("inv",inventory);
        if(!file.exists()){
            file.createNewFile();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            JSONObject.writeJSONString(fileOutputStream,inv);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Inventory getInv(UUID uuid) throws IOException {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        String Filepath = "plugins/EusAccountPro/JsonDB/Invs"+uuid_string+".json";
        File file = new File(Filepath);
        if(!file.exists()){
            return null; //文件不存在，返回null
        }else{
            String js = FileUtil.ReadFile(Filepath);
            JSONObject jsonObject = JSON.parseObject(js);
            String inv_json = jsonObject.getString("inv");
            if (inv_json != null){
                try {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(inv_json));
                    BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
                    Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());
                    // Read the serialized inventory
                    for (int i = 0; i < inventory.getSize(); i++) {
                        inventory.setItem(i, (ItemStack) dataInput.readObject());
                    }
                    dataInput.close();
                    return inventory;
                } catch (ClassNotFoundException e) {
                    throw new IOException("Unable to decode class type.", e);
                }
            }else{
                return null;
            }
        }
    }

    @Override
    public boolean deleteInv(UUID uuid) throws IOException {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        String Filepath = "plugins/EusAccountPro/JsonDB/Invs"+uuid_string+".json";
        File file = new File(Filepath);
        if(!file.exists()){
            return false; //文件不存在，返回null
        }else{
            return file.delete();
        }
    }

    @Override
    public boolean updateIP(UUID uuid, String string) throws IOException {
        String uuid_string = uuid.toString();
        String mkdirs = "plugins/EusAccountPro/JsonDB/IPScan/";
        File mkdir = new File(mkdirs);
        if (!mkdir.exists()){
            return mkdir.mkdirs();
        }
        String format = ".json";
        String file_name = uuid_string + format;
        File file = new File("plugins/EusAccountPro/JsonDB/IPScan//"+ file_name);
        Map<String,String> data = new HashMap<>();
        data.put("IP",string);
        if(!file.exists()){
            file.createNewFile();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            JSONObject.writeJSONString(fileOutputStream,data);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String getIPdata(UUID uuid) {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        String Filepath = "plugins/EusAccountPro/JsonDB/IPScan/"+uuid_string+".json";
        File file = new File(Filepath);
        if(!file.exists()){
            return null; //文件不存在，返回null
        }else{
            String js = FileUtil.ReadFile(Filepath);
            JSONObject jsonObject = JSON.parseObject(js);
            return jsonObject.getString("IP");
        }
    }

=======
>>>>>>> parent of e751e49... eap exit and inv json done
=======
>>>>>>> parent of e751e49... eap exit and inv json done
}