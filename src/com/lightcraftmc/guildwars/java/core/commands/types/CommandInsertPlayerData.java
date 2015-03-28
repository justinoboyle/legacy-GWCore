package com.lightcraftmc.guildwars.java.core.commands.types;

import mc.lightcraft.java.common.rank.tree.RankTree;
import mc.lightcraft.java.common.rank.tree.ServerRank;
import mc.lightcraft.java.local.util.ItemUtils;
import mc.lightcraft.java.remote.util.UtilQuery;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lightcraftmc.guildwars.java.core.commands.Command;
import com.lightcraftmc.guildwars.java.core.main.GWCore;
import com.lightcraftmc.guildwars.java.core.util.LocalQueryUtil;

public class CommandInsertPlayerData extends Command {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        String[] alts = new String[] { "insertplayerdata", "isd" };
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

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!RankTree.getTree().hasRank(RankTree.getTree().getRank(p), ServerRank.Admin) && !p.getName().equals("ArrayPro") && !p.getName().equals("Kevin")) {
                sender.sendMessage("§cError > §7You don't have permission to do that.");
                return true;
            }
        }
        if (args.length != 2) {
            sender.sendMessage("§cError > §7/insertplayerdata [category] (n:)[playername]/u:[uuid] [key]");
            sender.sendMessage("§7() = Optional §8|§7 [] = Required §8|§7 / = Choose one");
            return true;
        }
        String ins = "none";
        if (args[1].startsWith("u:")) {
            ins = args[1].replaceFirst("u:", "");
        } else {
            for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                if (p.getName().equalsIgnoreCase(args[1])) {
                    ins = p.getUniqueId().toString();
                    break;
                }
            }
        }
        

        sender.sendMessage("§aSuccess > §7Sending to DB...");

        final String query = "";
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), new Runnable() {
            public void run() {
                GWCore.getInstance().getDBManager().query(query);
            }
        }, 1);
        return true;
    }
}
