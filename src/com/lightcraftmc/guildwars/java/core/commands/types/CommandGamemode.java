package com.lightcraftmc.guildwars.java.core.commands.types;

import mc.lightcraft.java.common.rank.tree.RankTree;
import mc.lightcraft.java.common.rank.tree.ServerRank;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.commands.Command;

public class CommandGamemode extends Command {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        String[] alts = new String[] { "gamemode", "gm" };
        boolean cont = false;
        for (String s : alts) {
            if (label.equalsIgnoreCase(s)) {
                cont = true;
                break;
            }
        }

        if (!cont)
            return false;

        Player target = null;
        GameMode targetMode = null;
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!RankTree.getTree().hasRank(RankTree.getTree().getRank(p), ServerRank.Admin)) {
                sender.sendMessage("§cError > §7You don't have permission to do that.");
                return true;
            }
            if (args.length < 2) {
                target = p;
            }
            if (args.length == 0) {
                targetMode = p.getGameMode().equals(GameMode.CREATIVE) ? GameMode.ADVENTURE : GameMode.CREATIVE;
            } else {
                for (GameMode m : GameMode.values()) {
                    if (args[0].toLowerCase().startsWith(m.toString().toLowerCase())) {
                        targetMode = m;
                    }
                }
                if (targetMode == null) {
                    try {
                        targetMode = GameMode.getByValue(Integer.parseInt(args[0]));
                    } catch (Exception ex) {
                        sender.sendMessage("§cError > §7Incorrect game-mode!");
                        return true;
                    }
                }
            }

            if (args.length > 1)
                target = Bukkit.getPlayer(args[1]);
        } else {
            sender.sendMessage("This must be run by a player.");
        }

        if (targetMode == null) {
            sender.sendMessage("§cError > §7Incorrect game-mode!");
            return true;
        }

        if (target == null) {
            sender.sendMessage("§cError > §7Incorrect target player!");
            return true;
        }

        target.setGameMode(targetMode);

        sender.sendMessage("§aSuccess > §7Set " + target.getName() + " §7to " + targetMode.toString().toLowerCase() + " mode.");

        return true;
    }

}
