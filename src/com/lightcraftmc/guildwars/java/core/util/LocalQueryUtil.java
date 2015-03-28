package com.lightcraftmc.guildwars.java.core.util;

import mc.lightcraft.java.common.rank.tree.RankTree;
import mc.lightcraft.java.common.rank.tree.ServerRank;
import mc.lightcraft.java.remote.util.UtilQuery;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.lightcraftmc.guildwars.java.core.main.GWCore;

public class LocalQueryUtil {
    @SuppressWarnings("deprecation")
    public static void handleBan(Player p) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), new Runnable() {
            public void run() {
                String query = GWCore.getInstance().getDBManager().query(UtilQuery.getBannedQuery(p));
                if (query == null) {
                    return;
                }
                if ((!query.equals("")) && (!query.equals(" ")) && (query.contains("true"))) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                        public void run() {
                            p.kickPlayer("§cYou are banned!");
                        }
                    }, 1L);
                }
            }
        }, 1L);
    }

    @SuppressWarnings("deprecation")
    public static void handleRank(Player p, final boolean giveCapabilities) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), new Runnable() {
            public void run() {
                String query = GWCore.getInstance().getDBManager().query(UtilQuery.getRankQuery(p));
                ServerRank r2 = ServerRank.Default;
                for (ServerRank rr : RankTree.getTree().getTreeList()) {
                    if (rr.toString().equalsIgnoreCase(query)) {
                        r2 = rr;
                    }
                }
                final ServerRank r = r2;
                RankTree.getTree().setRank(p, r);
                Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                    public void run() {
                        p.setMetadata("rank", new FixedMetadataValue(GWCore.getInstance(), RankTree.getTree().getRank(p).toString()));
                        if (giveCapabilities) {
                            if (RankTree.getTree().hasRank(r, ServerRank.VIP)) {
                                p.setAllowFlight(true);
                            }
                        }
                    }
                }, 1L);
            }
        }, 1L);
    }
}
