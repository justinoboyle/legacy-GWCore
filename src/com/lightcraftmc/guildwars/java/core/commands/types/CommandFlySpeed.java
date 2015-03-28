package com.lightcraftmc.guildwars.java.core.commands.types;

import mc.lightcraft.java.common.rank.tree.RankTree;
import mc.lightcraft.java.common.rank.tree.ServerRank;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.commands.Command;

public class CommandFlySpeed extends Command {

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        String[] alts = new String[] { "flyspeed", "fs" };
        boolean cont = false;
        for (String s : alts) {
            if (label.equalsIgnoreCase(s)) {
                cont = true;
                break;
            }
        }

        if (!cont)
            return false;

        if (!(sender instanceof Player))
            return true;

        Player p = (Player) sender;
        if (!RankTree.getTree().hasRank(RankTree.getTree().getRank(p), ServerRank.Helper)) {
            sender.sendMessage("§cError > §7You don't have permission to do that.");
            return true;
        }

        float d = 2;

        try {
            d = Float.parseFloat(args[0]);
        } catch (Exception ex) {
            sender.sendMessage("§cUsage > §7/flyspeed [speed]");
            return true;
        }
        d = d / 10;
        if (d > 1 || d < 0) {
            sender.sendMessage("§cUsage > §7/flyspeed [speed]");
            return true;
        }
        p.setFlySpeed(d);
        sender.sendMessage("§aSuccess > §7You are now at fly-speed " + d*10 + " (" + d + ")");

        return true;
    }
}
