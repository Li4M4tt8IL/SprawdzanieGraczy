package me.tnl.sprawdzanie.events;

import me.tnl.sprawdzanie.Sprawdzanie;
import me.tnl.sprawdzanie.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.Objects;

public class Events implements Listener {
    private Sprawdzanie plugin;
    public Events(Sprawdzanie plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if(Sprawdzanie.sprawdzani.containsValue(p.getName())) {
            String administrator = "";
            for(Map.Entry<String, String> entry : Sprawdzanie.sprawdzani.entrySet()) {
                if(entry.getValue().equalsIgnoreCase(p.getName())) {
                    administrator = entry.getKey();
                }
            }
            Player admin = Bukkit.getPlayer(administrator);
            assert admin != null;
            if(admin.isOnline()) {
                Utils.sendColorMessage(admin, String.format("Gracz &c%s&r wylogowal sie w trakcie sprawdzania!", p.getName()));
            }
            Sprawdzanie.sprawdzani.remove(administrator, p.getName());
            Utils.sprawdzanieLogout(admin, p);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String command = e.getMessage().split(" ")[0];
        if(Sprawdzanie.sprawdzani.containsValue(p.getName())) {
            if(Utils.isCommandWhitelisted(command.replace("/", ""))) {
                return;
            } else {
                Utils.sendColorMessage(p, "&cNie mozesz uzywac komend w trakcie sprawdzania!");
                e.setCancelled(true);
            }
        }
    }
}
