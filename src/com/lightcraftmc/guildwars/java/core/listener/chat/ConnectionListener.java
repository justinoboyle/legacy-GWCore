package com.lightcraftmc.guildwars.java.core.listener.chat;

import mc.lightcraft.java.common.rank.tree.RankTree;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.lightcraftmc.guildwars.java.core.currency.Currency;
import com.lightcraftmc.guildwars.java.core.main.GWCore;
import com.lightcraftmc.guildwars.java.core.main.GWScoreboard;
import com.lightcraftmc.guildwars.java.core.util.LocalQueryUtil;
import com.lightcraftmc.guildwars.java.util.classes.UtilClass;
import com.lightcraftmc.guildwars.java.util.schedule.UtilSchedule;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.teleport(Bukkit.getWorld(GWCore.getInstance().getConfig().getString("spawnworld")).getSpawnLocation());
        p.setGameMode(GameMode.ADVENTURE);
        RankTree.getTree().unloadRank(e.getPlayer());
        p.updateInventory();
        LocalQueryUtil.handleBan(p);
        LocalQueryUtil.handleRank(p, false);
        GWScoreboard.setupScoreboard(e.getPlayer());
        Currency.syncGlobal(e.getPlayer());
        UtilClass.sync(e.getPlayer());
        String ip = e.getPlayer().getAddress().toString();
        UtilSchedule.scheduleAsync(new Runnable() {
            public void run() {
                if (!ip.startsWith("192.168")) {
                    // stats
                }
            }
        });
        e.setJoinMessage(null);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        RankTree.getTree().unloadRank(e.getPlayer());
        Currency.disconnectGlobal(e.getPlayer().getUniqueId().toString());
    }


}
