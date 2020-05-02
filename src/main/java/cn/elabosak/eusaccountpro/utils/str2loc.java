package cn.elabosak.eusaccountpro.utils;

import org.bukkit.Location;

import static org.bukkit.Bukkit.getServer;

public class str2loc {

    public static Location str2loc(String str){

        String str2loc[]=str.split("\\:");

        Location loc = new Location(getServer().getWorld(str2loc[0]),0,0,0);

        loc.setX(Double.parseDouble(str2loc[1]));

        loc.setY(Double.parseDouble(str2loc[2]));

        loc.setZ(Double.parseDouble(str2loc[3]));

        return loc;

    }



    public static String loc2str(Location loc){

        return loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();

    }

}
