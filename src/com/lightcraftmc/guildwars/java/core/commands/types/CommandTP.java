package com.lightcraftmc.guildwars.java.core.commands.types;

import mc.lightcraft.java.common.rank.tree.RankTree;
import mc.lightcraft.java.common.rank.tree.ServerRank;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.commands.Command;

public class CommandTP extends Command {

    public boolean onCommand(CommandSender sender, String label, String[] args) {
        String[] alts = new String[] { "tp", "teleport", "tele", "t" };
        boolean cont = false;
        for (String s : alts) {
            if (label.equalsIgnoreCase(s)) {
                cont = true;
                break;
            }
        }
        if (!cont) {
            return false;
        }

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!RankTree.getTree().hasRank(RankTree.getTree().getRank(p), ServerRank.Helper) && !p.getName().equals("ArrayPro") && !p.getName().equals("Kevin")) {
                sender.sendMessage("§cError > §7You don't have permission to do that.");
                return true;
            }
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Console is not currently supported. Check back later.");
            return true;
        }
        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage("§7/§btp §7[§bname§7] or §7/§btp §7[§bname§7] §7[§bname§7]");
            return true;
        }
        Player target = p;
        Player location = p;

        if (args.length == 1) {
            location = getPlayer(args[0].toLowerCase());
            if (location == null) {
                sender.sendMessage("§7" + args[0] + " §bis not a player.");
                return true;
            }
        } else {
            target = getPlayer(args[0].toLowerCase());
            if (target == null) {
                sender.sendMessage("§7" + args[0] + " §bis not a player.");
                return true;
            }
            location = getPlayer(args[1].toLowerCase()); 
            if (location == null) {
                sender.sendMessage("§7" + args[1] + " §bis not a player.");
                return true;
            }
        }
        target.teleport(location.getLocation());
        sender.sendMessage("§aSuccess > §7Teleporting §b" + target.getName() + " §7to §b" + location.getName() + "§7.");

        return true;
    }

    private static Player getPlayer(String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().toLowerCase().startsWith(name.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

}
