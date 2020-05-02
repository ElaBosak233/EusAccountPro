package cn.elabosak.eusaccountpro.database;

import org.bukkit.Location;
import java.io.IOException;
import java.util.UUID;

public abstract class Database {

    public abstract String getSecretKey(UUID uuid) throws IOException; //请求输入玩家uuid，生成密钥

    public abstract boolean updatePlayer(UUID uuid, String secretKey) throws IOException; //请求输入玩家uuid和密钥进行玩家二步验证数据初始化

    public abstract boolean isPlayerRegistered(UUID uuid); //请求输入玩家uuid判断玩家是否已注册

    public abstract boolean deletePlayer(UUID uuid) throws IOException; //请求输入玩家uuid和数据库类型进行删除

    public abstract boolean SafePoint(UUID uuid, Location safepoint) throws IOException; //请求输入玩家uuid和位置记录安全点

    public abstract Location getSafePoint(UUID uuid) throws IOException; //请求输入玩家uuid获取安全点

    public abstract Boolean updateStauts(UUID uuid, String stauts) throws IOException;

    public abstract String getStauts(UUID uuid) throws IOException;

}
