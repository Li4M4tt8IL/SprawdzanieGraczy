package me.tnl.sprawdzanie.utils;

import me.tnl.sprawdzanie.Sprawdzanie;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompletor implements TabCompleter {
    private Sprawdzanie plugin;

    public TabCompletor(Sprawdzanie plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("sprawdz")) {
            if(args.length == 1) {
                List<String> list = new ArrayList<>();
                list.add("start");
                list.add("czysty");
                list.add("cheater");
                list.add("brakwsp");
                list.add("setroom");
                list.add("setspawn");

                return list;
            }
        }

        return null;
    }
}
