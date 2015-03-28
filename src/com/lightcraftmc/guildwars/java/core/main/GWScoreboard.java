package com.lightcraftmc.guildwars.java.core.main;

import java.util.ArrayList;
import java.util.Collections;

import mc.lightcraft.java.common.rank.tree.RankTree;
import mc.lightcraft.java.common.rank.tree.ServerRank;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.lightcraftmc.guildwars.java.core.currency.Currencies;
import com.lightcraftmc.guildwars.java.core.util.BUtils;
import com.lightcraftmc.guildwars.java.core.util.calculation.UtilCalculation;
import com.lightcraftmc.guildwars.java.util.classes.GameClass;
import com.lightcraftmc.guildwars.java.util.classes.UtilClass;
import com.lightcraftmc.guildwars.java.util.display.UtilDouble;
import com.lightcraftmc.guildwars.java.util.schedule.UtilSchedule;

public class GWScoreboard {

    public static void setupScoreboards() {

        for (Player p2 : Bukkit.getOnlinePlayers()) {
            setupScoreboard(p2);
        }

    }

    public static void setupScoreboard(Player p2) {
        BUtils.sendHeaderAndFooter(p2, "§9§lGuild Wars", "§7§oNext war in 3 hours.");
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective sidebar = board.registerNewObjective("sidebar", "dummy");
        Objective xp = board.registerNewObjective("experience", "dummy");

        for (Player p : Bukkit.getOnlinePlayers()) {
            String pfx = "§7";
            String sfx = "";
            long level = UtilCalculation.getLevel(Currencies.EXPERIENCE.getSafely(p.getUniqueId().toString()));
            GameClass cl = UtilClass.getClassSafely(p.getUniqueId().toString());
            if (!RankTree.getTree().getRank(p).equals(ServerRank.Default)) {
                pfx = "§7[" + RankTree.getTree().getColor(RankTree.getTree().getRank(p)) + RankTree.getTree().getRank(p).toString().charAt(0) + "§7] "
                        + RankTree.getTree().getColor(RankTree.getTree().getRank(p));
            } else {
                pfx = "§7";
            }

            sfx = "§7 [" + cl.getColor() + cl.getTrimName() + " §7" + level + "]";
            sfx = sfx.substring(0, Math.min(sfx.length(), 16));
            setupTeam(p, pfx, sfx, board);
        }
        double exp = Currencies.EXPERIENCE.getSafely(p2.getUniqueId().toString());
        double coins = Currencies.COINS.getSafely(p2.getUniqueId().toString());
        long level = UtilCalculation.getLevel(exp);
        GameClass cl = UtilClass.getClassSafely(p2.getUniqueId().toString());
        ArrayList<String> boardMessages = new ArrayList<String>();
        boardMessages.add("§c");
        boardMessages.add("§7§lPlayer:    ");
        boardMessages.add("§a" + p2.getName());
        boardMessages.add("");
        boardMessages.add("§7§lRank:      ");
        boardMessages.add(RankTree.getTree().getColor(RankTree.getTree().getRank(p2)) + RankTree.getTree().getRank(p2).toString());
        boardMessages.add(" ");
        boardMessages.add("§7§lCoins:     ");
        boardMessages.add("§6" + UtilDouble.getString(coins));
        boardMessages.add("  ");
        boardMessages.add("§7§lClass:     ");
        boardMessages.add("§9" + WordUtils.capitalize(cl.toString().toLowerCase().replace("_", " ")));
        boardMessages.add("     ");
        boardMessages.add("§7§lLevel:     ");
        boardMessages.add("§b" + level);
        boardMessages.add("§c§d");
        Collections.reverse(boardMessages);

        int i = 0;
        for (String s : boardMessages) {
            if (s.length() > 16) {
                s = "§7...";
            }
            Score score = sidebar.getScore(s);
            score.setScore(i);
            i++;
        }
        sidebar.setDisplayName("§9§lGuild Wars");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        xp.setDisplayName("Experience");
        p2.setScoreboard(board);

        /*
         * for (Guild g : Guild.getGuilds()) {
         * //Bukkit.broadcastMessage(g.getName()); if
         * (g.getMembers().containsKey(p.getUniqueId().toString()) ||
         * g.getOwner().equals(p.getUniqueId().toString())) { sfx = " §7[" +
         * g.getName().substring(0, Math.min(g.getName().length(),
         * 6)).toUpperCase() + "§7]"; break; } }
         */

    }

    public String getSuffix(String uuid) {
        return " §7[" + UtilCalculation.getLevel(getExperience(uuid)) + "]";
    }

    private static double getExperience(String uuid) {
        String query = GWCore.getInstance().getDBManager().query("retrieve players/exp " + uuid);
        if (query.equals("") || query.equals(" ")) {
            return 0;
        }

        double i = 0;
        try {
            i = Double.parseDouble(query);
            UtilSchedule.scheduleAsync(new Runnable() {
                public void run() {
                    GWCore.getInstance().getDBManager().query("insert players/exp " + uuid + " 0");
                }
            });
        } catch (Exception ex) {
            i = 0;
        }
        return i;
    }

    public static void setupTeam(OfflinePlayer p, String pfx, String sfx, Scoreboard board) {
        // deleteTeam(p);
        Team team = board.registerNewTeam(p.getName() + "Team");
        team.addPlayer(p);
        team.setPrefix(pfx);
        team.setSuffix(sfx);

    }

    public static void deleteTeam(OfflinePlayer p, Scoreboard board) {
        try {
            if (board.getPlayerTeam(p) != null) {
                board.getPlayerTeam(p).removePlayer(p);
                board.getTeams().remove(board.getPlayerTeam(p));
                board.getPlayerTeam(p).unregister();
            }
        } catch (Exception ex) {

        }
        try {
            if (board.getTeam(p.getName() + "Team") != null) {
                board.getTeams().remove(board.getTeam(p.getName() + "Team"));
                board.getTeam(p.getName() + "Team").unregister();
            }
        } catch (Exception ex) {

        }
    }

}
