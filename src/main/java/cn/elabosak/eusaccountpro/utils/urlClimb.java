package cn.elabosak.eusaccountpro.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class urlClimb {
    public static String urlClimb(String url) throws Exception{
        URL getUrl =new URL(url); //创建URl连接
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection(); //建立连接
        connection.connect(); //打开连接
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")); //创建输入流并设置编码
        StringBuffer sb = new StringBuffer();
        String lines = null;
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8"); //读取流的一行,设置编码
            sb = sb.append(lines + "\n");
        }
        reader.close(); //关闭流
        connection.disconnect(); //销毁连接
        return sb.toString(); //返回抓取的数据(注意,这里是抓取了访问的网站的全部数据)
    }
}
