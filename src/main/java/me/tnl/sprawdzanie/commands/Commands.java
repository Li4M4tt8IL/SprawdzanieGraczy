package me.tnl.sprawdzanie.commands;

import me.tnl.sprawdzanie.Sprawdzanie;
import me.tnl.sprawdzanie.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Commands implements CommandExecutor {
    private Sprawdzanie plugin;
    public Commands(Sprawdzanie plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName()) {
            case "sprawdzaniereload":
                if(sender.hasPermission("tnl.pluginreload")) {
                    plugin.reloadConfig();
                    sender.sendMessage(Utils.color("Przeladowano config!"));
                    return true;
                }
            case "sprawdz": {
                if(sender.hasPermission("tnl.sprawdz") && (sender instanceof Player)) {
                    Player p = ((Player) sender);
                    if(args.length > 2 || args.length < 1) {
                        Utils.sendColorMessage(p, "Niepoprawne uzycie komendy! /sprawdz <start|czysty|cheater|brakwsp|setroom|setspawn> <nick>");
                        return true;
                    }

                    switch (args[0]) {
                        case "start": {
                            Player target = Bukkit.getPlayer(args[1]);
                            if(!target.isOnline()) {
                                Utils.sendColorMessage(p, "Gracz nie jest online!");
                                return true;
                            }
                            Utils.sendColorMessage(p, Utils.sprawdzanieStart(p, target));
                            break;
                        }
                        case "czysty": {
                            Player target = Bukkit.getPlayer(args[1]);
                            if(!target.isOnline()) {
                                Utils.sendColorMessage(p, "Gracz nie jest online!");
                                return true;
                            }
                            Utils.sendColorMessage(p, Utils.sprawdzanieCzysty(p, target));
                            break;
                        }
                        case "cheater": {
                            Player target = Bukkit.getPlayer(args[1]);
                            if(!target.isOnline()) {
                                Utils.sendColorMessage(p, "Gracz nie jest online!");
                                return true;
                            }
                            Utils.sprawdzanieCheater(p, target);
                            break;
                        }
                        case "brakwsp": {
                            Player target = Bukkit.getPlayer(args[1]);
                            if(!target.isOnline()) {
                                Utils.sendColorMessage(p, "Gracz nie jest online!");
                                return true;
                            }
                            Utils.sprawdzanieBrakWsp(p, target);
                            break;
                        }
                        case "setroom": {
                            if(p.hasPermission("tnl.sprawdz.setroom")) {
                                Utils.setNewSprawdzarka(p.getLocation());
                                Utils.sendColorMessage(p, "Ustawiono nowa sprawdzarke!");
                            }
                            break;
                        }
                        case "setspawn": {
                            if(p.hasPermission("tnl.sprawdz.setroom")) {
                                Utils.setNewSpawn(p.getLocation());
                                Utils.sendColorMessage(p, "Ustawiono spawn!");
                            }
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }
}
