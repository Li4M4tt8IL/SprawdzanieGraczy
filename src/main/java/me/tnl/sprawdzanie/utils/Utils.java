package me.tnl.sprawdzanie.utils;

import me.NoChance.PvPManager.PvPManager;
import me.NoChance.PvPManager.PvPlayer;
import me.tnl.sprawdzanie.Sprawdzanie;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Map;

public class Utils {
    public static Sprawdzanie plugin = Sprawdzanie.getPlugin(Sprawdzanie.class);

    public static String color(String msg) {
        String coloredMsg = "";
        for(int i = 0; i < msg.length(); i++)
        {
            if(msg.charAt(i) == '&')
                coloredMsg += '§';
            else
                coloredMsg += msg.charAt(i);
        }
        return coloredMsg;
    }

    public static void sendColorMessage(Player p, String s) {
        p.sendMessage(color(plugin.getConfig().getString("prefix")+s));
    }

    public static String sprawdzanieStart(Player p, Player target) {
        if(Sprawdzanie.sprawdzani.containsValue(target.getName())) {
            String administrator = "";
            for(Map.Entry<String, String> entry : Sprawdzanie.sprawdzani.entrySet()) {
                if(entry.getValue().equalsIgnoreCase(target.getName())) {
                    administrator = entry.getKey();
                }
            }
            return String.format("Gracz &c%s &rjuz jest sprawdzany przez &a%s&r!", target.getName(), administrator);
        } else {
            PvPlayer PvPTarget = PvPlayer.get(target);
            PvPTarget.unTag();
            target.sendTitle(color(plugin.getConfig().getString("title")), color(plugin.getConfig().getString("subtitle")), 10, plugin.getConfig().getInt("stay"), 10);
            Sprawdzanie.sprawdzani.put(p.getName(), target.getName());
            teleportToSprawdzarka(p);
            teleportToSprawdzarka(target);
            return String.format("Rozpoczeto sprawdzanie gracza &c%s&r!", target.getName());
        }
    }

    public static String sprawdzanieCzysty(Player p, Player target) {
        if(!Sprawdzanie.sprawdzani.containsValue(target.getName())) {
            return String.format("Gracz &c%s &rnie byl sprawdzany!", target.getName());
        } else {
            teleportToBack(target);
            Sprawdzanie.sprawdzani.remove(p.getName(), target.getName());
            return "&aSprawdzanie zakonczone!";
        }
    }

    public static void sprawdzanieBrakWsp(Player p, Player target) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban "+target.getName()+" Brak współpracy w trakcie sprawdzania ~"+p.getName());
        Sprawdzanie.sprawdzani.remove(p.getName(), target.getName());
    }

    public static void sprawdzanieLogout(Player p, Player target) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban "+target.getName()+" Logout w trakcie sprawdzania ~"+p.getName());
        Sprawdzanie.sprawdzani.remove(p.getName(), target.getName());
    }

    public static void sprawdzanieCheater(Player p, Player target) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban "+target.getName()+" Wykrycie cheatów w trakcie sprawdzania ~"+p.getName());
        Sprawdzanie.sprawdzani.remove(p.getName(), target.getName());
    }


    public static void teleportToBack(Player p) {
        teleportToSpawn(p);
        sendColorMessage(p, "&aSprawdzanie zakonczone!");
    }

    public static void teleportToSprawdzarka(Player p) {
        World world = Bukkit.getServer().getWorld(plugin.getConfig().getString("Sprawdzarka.world"));
        double x = plugin.getConfig().getDouble("Sprawdzarka.x");
        double y = plugin.getConfig().getDouble("Sprawdzarka.y");
        double z = plugin.getConfig().getDouble("Sprawdzarka.z");
        Location loc = new Location(world, x, y, z);
        p.teleport(loc);
    }

    public static void setNewSprawdzarka(Location loc) {
        plugin.getConfig().set("Sprawdzarka.x", loc.getX());
        plugin.getConfig().set("Sprawdzarka.y", loc.getY());
        plugin.getConfig().set("Sprawdzarka.z", loc.getZ());
        plugin.getConfig().set("Sprawdzarka.world", loc.getWorld().getName());

        plugin.saveConfig();
    }

    public static void teleportToSpawn(Player p) {
        World world = Bukkit.getServer().getWorld(plugin.getConfig().getString("Spawn.world"));
        double x = plugin.getConfig().getDouble("Spawn.x");
        double y = plugin.getConfig().getDouble("Spawn.y");
        double z = plugin.getConfig().getDouble("Spawn.z");
        Location loc = new Location(world, x, y, z);
        p.teleport(loc);
    }

    public static void setNewSpawn(Location loc) {
        plugin.getConfig().set("Spawn.x", loc.getX());
        plugin.getConfig().set("Spawn.y", loc.getY());
        plugin.getConfig().set("Spawn.z", loc.getZ());
        plugin.getConfig().set("Spawn.world", loc.getWorld().getName());

        plugin.saveConfig();
    }

    public static boolean isCommandWhitelisted(String command) {
        for(String string : plugin.getConfig().getStringList("Whitelisted-Commands")) {
            if(string.equalsIgnoreCase(command)) {
                return true;
            }
        }
        return false;
    }
}