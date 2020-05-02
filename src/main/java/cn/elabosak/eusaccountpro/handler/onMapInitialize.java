package cn.elabosak.eusaccountpro.handler;

import cn.elabosak.eusaccountpro.EusAccountPro;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;

import javax.swing.*;

public class onMapInitialize implements Listener {

    EusAccountPro plugin;

    @EventHandler
    public void onMapInitialize(MapInitializeEvent event){
        MapView map = event.getMap();
        for (MapRenderer r : map.getRenderers())
            map.removeRenderer(r);
        map.addRenderer(new MapRenderer() {
            @Override
            public void render(MapView view, MapCanvas canvas, Player player) {
                canvas.drawImage(0,0,new ImageIcon("plugins/EusAccountPro/QRCode/"+player.getUniqueId().toString()+".png").getImage());
            }
        });
    }
}
