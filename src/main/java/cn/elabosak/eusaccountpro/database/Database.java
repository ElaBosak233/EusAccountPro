package cn.elabosak.eusaccountpro.database;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public abstract class Database {

    public abstract String getSecretKey(UUID uuid) throws IOException; //请求输入玩家uuid，生成密钥

    public abstract boolean updatePlayer(UUID uuid, String secretKey) throws IOException; //请求输入玩家uuid和密钥进行玩家二步验证数据初始化

    public abstract boolean isPlayerRegistered(UUID uuid); //请求输入玩家uuid判断玩家是否已注册

    public abstract boolean deletePlayer(UUID uuid); //请求输入玩家uuid和数据库类型进行删除

    public abstract boolean SafePoint(UUID uuid, Location safepoint) throws IOException; //请求输入玩家uuid和位置记录安全点

    public abstract Location getSafePoint(UUID uuid) throws IOException; //请求输入玩家uuid获取安全点

    public abstract boolean updateInv(UUID uuid, Inventory inventory) throws IOException; //保存玩家物品栏至数据库

    public abstract Inventory getInv(UUID uuid) throws IOException; //获取数据库中的物品栏

    public abstract boolean deleteInv(UUID uuid) throws IOException; //删除物品栏

    public abstract boolean updateGeoIP(UUID uuid, com.maxmind.geoip2.record.Location location) throws IOException; //更新GeoIP数据库

    public abstract String getGeoIP(UUID uuid); //获取GeoIP

}
