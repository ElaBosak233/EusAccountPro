package cn.elabosak.eusaccountpro.database;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.elabosak.eusaccountpro.utils.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import cn.elabosak.eusaccountpro.EusAccountPro;
import cn.elabosak.eusaccountpro.utils.str2loc;

public class JsonDB extends Database {

    EusAccountPro plugin;

    @Override
    public String getSecretKey(UUID uuid) {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        String Filepath = "plugins/EusAccountPro/JsonDB/SecretKey/"+uuid_string+".json";
        File file = new File(Filepath);
        if(!file.exists()){
            return null; //文件不存在，返回null
        }else{
            String js = FileUtil.ReadFile(Filepath);
            JSONObject jsonObject = JSON.parseObject(js);
            String secretKey_json = jsonObject.getString("secretKey");
            if (secretKey_json != null){
                return secretKey_json;
            }else{
                return null;
            }
        }
    }

    @Override
    public boolean updatePlayer(UUID uuid, String secretKey) throws IOException {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        Map<String,String> data = new HashMap<String,String>();
        data.put("secretKey",secretKey);
        Object mapJson = JSONObject.toJSON(data);
        File mkdirs = new File("plugins/EusAccountPro/JsonDB/SecretKey/");
        if(!mkdirs.exists()){
            mkdirs.mkdirs();
        }
        String format = ".json";
        String file_name = uuid_string + format;
        File file = new File("plugins/EusAccountPro/JsonDB/SecretKey//"+file_name);
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
        File file = new File("plugins/EusAccountPro/JsonDB/SecretKey/"+uuid_string+".json");
        if(!file.exists()){
            return false; //文件不存在，返回false
        }else{
            return true;
        }
    }

    @Override
    public boolean deletePlayer(UUID uuid) throws IOException {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        File file = new File("plugins/EusAccountPro/JsonDB/SecretKey/"+uuid_string+".json");
        File QRCode = new File("plugins/EusAccountPro/QRCode/"+uuid_string+".png");
        if(!file.exists() && !QRCode.exists()){
            return false; //文件不存在，返回false
        }else{
//            FileUtils.forceDeleteOnExit(file);
//            FileUtils.forceDeleteOnExit(QRCode);
            file.delete();
            QRCode.delete();
            return true;
        }
    }

    @Override
    public boolean SafePoint(UUID uuid, Location safepoint) throws IOException {
        String uuid_string = uuid.toString(); //将uuid的数据类型转换为String
        Map<String,String> loc = new HashMap<String,String>();
        loc.put("safepoint",str2loc.loc2str(safepoint));
        Object locJson = JSONObject.toJSON(loc);
        File mkdirs = new File("plugins/EusAccountPro/JsonDB/SafePoint/");
        if(!mkdirs.exists()){
            mkdirs.mkdirs();
        }
        String format = ".json";
        String file_name = uuid_string + format;
        File file = new File("plugins/EusAccountPro/JsonDB/SafePoint//"+file_name);
        if(!file.exists()){
            file.createNewFile();
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

}