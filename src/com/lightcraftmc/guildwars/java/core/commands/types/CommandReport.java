package com.lightcraftmc.guildwars.java.core.commands.types;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.commands.Command;
import com.lightcraftmc.guildwars.java.core.main.GWCore;

public class CommandReport extends Command {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        String[] alts = new String[] { "rep", "report" };
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

        if (args.length < 2) {
            sender.sendMessage("§bUsage > §7/report [player] (reason)");
            return true;
        }

        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;

        OfflinePlayer target = null;

        for (OfflinePlayer p2 : Bukkit.getOfflinePlayers()) {
            if (p2.getName().equalsIgnoreCase(args[0])) {
                target = p2;
                break;
            }
        }

        if (target == null) {
            for (OfflinePlayer p2 : Bukkit.getOfflinePlayers()) {
                if (p2.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    target = p2;
                    break;
                }
            }
        }

        if (target == null) {
            p.sendMessage("§cError > §7That is not a valid player!");
            return true;
        }

        String builder = "";

        int i = 0;
        boolean first = true;
        for (String s : args) {
            if (i != 0) {
                if (first) {
                    builder = s;
                    first = false;
                } else {
                    builder = builder + " " + s;
                }
            }
            i++;
        }
        builder = builder.replace(" ", "_");
        builder = builder.replace("%20", "_");
        builder = builder.replace("!!", "_");
        builder = builder.replace("=", "_");
        builder = builder.replace("#", "_");
        builder = builder.substring(0);

        if (target.isOnline()) {
            Player t = target.getPlayer();
            if (t == p) {
                p.sendMessage("§cError > §7You can't report yourself.");
                return true;
            }
            t.playSound(t.getLocation(), Sound.NOTE_BASS, 0.5f, 0.5f);
            t.sendMessage("§8§m--------------------------------------------");
            t.sendMessage("§c§lYou have been reported by an anonymous player. Consequences will be dispatched if neccessary, regardless of ranks.");
            t.sendMessage("§bThere will be staff watching you.");
            t.sendMessage("§8§m--------------------------------------------");
        }
        p.sendMessage("§8§m--------------------------------------------");
        p.sendMessage("§c§lPlayer has been reported. ");
        p.sendMessage("§bThank you!");
        p.sendMessage("§8§m--------------------------------------------");

        final Player from = p;
        final OfflinePlayer target2 = target;
        final String reason = builder;

        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), new Runnable() {
            public void run() {
                GWCore.getInstance().getDBManager().query("insert players\\reports\\" + target2.getUniqueId() + " " + from.getUniqueId() + " " + reason);
            }
        }, 1);

        return true;
    }

}
