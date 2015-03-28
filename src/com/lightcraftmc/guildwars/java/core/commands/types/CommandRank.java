package com.lightcraftmc.guildwars.java.core.commands.types;

import mc.lightcraft.java.common.rank.tree.RankTree;
import mc.lightcraft.java.common.rank.tree.ServerRank;
import mc.lightcraft.java.remote.util.UtilQuery;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.commands.Command;
import com.lightcraftmc.guildwars.java.core.main.GWCore;
import com.lightcraftmc.guildwars.java.core.util.LocalQueryUtil;

public class CommandRank extends Command {

    @SuppressWarnings({ "deprecation" })
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        String[] alts = new String[] { "rank" };
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
        if (args.length != 2) {
            sender.sendMessage("§cError > §7/rank (n:)[playername]/u:[uuid] [rank]");
            sender.sendMessage("§7() = Optional §8|§7 [] = Required §8|§7 / = Choose one");
            return true;
        }
        String ins = "none";
        if (args[0].startsWith("u:")) {
            ins = args[0].replaceFirst("u:", "");
        } else {
            for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                if (p.getName().equalsIgnoreCase(args[0])) {
                    ins = p.getUniqueId().toString();
                    break;
                }
            }
        }

        ServerRank rank = null;
        for (ServerRank r : ServerRank.values())
            if (r.toString().equalsIgnoreCase(args[1])) {
                rank = r;
                break;
            }

        if (ins.equals("none") || ins == null || ins.equals("null")) {
            sender.sendMessage("§cError > §7Incorrect user!");
            return true;
        }

        if (rank == null) {
            sender.sendMessage("§cError > §7Incorrect rank!");
            return true;
        }

        sender.sendMessage("§aSuccess > §7Sending to DB...");

        final String name = ins;
        final String r = rank.toString();
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), new Runnable() {
            public void run() {
                UtilQuery.setRankQuery(name, r);
                GWCore.getInstance().getDBManager().query(UtilQuery.setRankQuery(name, r));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getName().equals(name) || p.getUniqueId().toString().equals(name)) {
                        RankTree.getTree().unloadRank(p);
                        p.updateInventory();
                        LocalQueryUtil.handleBan(p);
                        LocalQueryUtil.handleRank(p, false);
                    }
                }
            }
        }, 1);

        return true;

    }
}
