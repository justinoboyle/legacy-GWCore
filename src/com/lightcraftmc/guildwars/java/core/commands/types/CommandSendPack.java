package com.lightcraftmc.guildwars.java.core.commands.types;

import mc.lightcraft.java.common.rank.tree.RankTree;
import mc.lightcraft.java.common.rank.tree.ServerRank;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.commands.Command;

public class CommandSendPack extends Command {

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        String[] alts = new String[] { "sendpack" };
        boolean cont = false;
        for (String s : alts) {
            if (label.equalsIgnoreCase(s)) {
                cont = true;
                break;
            }
        }

        if (!cont)
            return false;

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!RankTree.getTree().hasRank(RankTree.getTree().getRank(p), ServerRank.Admin) && !p.getName().equals("ArrayPro") && !p.getName().equals("Kevin")) {
                sender.sendMessage("§cError > §7You don't have permission to do that.");
                return true;
            }
        }
        if (args.length != 1) {
            sender.sendMessage("§cError > §7/sendpack [pack]");
            return true;
        }
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setResourcePack(args[0]);
        }
        sender.sendMessage("§aSuccess > §7Sent!");
        return true;
    }

}
