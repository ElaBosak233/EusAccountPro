package cn.elabosak.eusaccountpro.utils;

import cn.elabosak.eusaccountpro.EusAccountPro;
import org.bukkit.Bukkit;

public class FileClean implements Runnable{

    EusAccountPro plugin;

    @Override
    public void run() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                System.gc();
            }
        });
    }
}
