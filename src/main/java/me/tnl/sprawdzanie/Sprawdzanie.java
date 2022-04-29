package me.tnl.sprawdzanie;

import me.NoChance.PvPManager.PvPManager;
import me.tnl.sprawdzanie.commands.Commands;
import me.tnl.sprawdzanie.events.Events;
import me.tnl.sprawdzanie.utils.TabCompletor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class Sprawdzanie extends JavaPlugin {

    private Commands commands;
    private Events events;
    private TabCompletor tabCompletor;

    public PvPManager pvpmanager;

    public static HashMap<String, String> sprawdzani;

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().isPluginEnabled("PvPManager")) pvpmanager = (PvPManager) Bukkit.getPluginManager().getPlugin("PvPManager");

        load();
        getCommand("sprawdzaniereload").setExecutor(commands);
        getCommand("sprawdz").setExecutor(commands);

        getCommand("sprawdz").setTabCompleter(tabCompletor);

        Bukkit.getPluginManager().registerEvents(events, this);
    }
    @Override
    public void onDisable() {
        saveConfig();
    }

    public void load() {
        saveDefaultConfig();
        commands = new Commands(this);
        events = new Events(this);
        tabCompletor = new TabCompletor(this);
        sprawdzani = new HashMap<>();
    }
}
