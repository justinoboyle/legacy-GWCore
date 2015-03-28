package com.lightcraftmc.guildwars.java.core.listener.world;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.lightcraftmc.guildwars.java.core.main.GWScoreboard;
import com.lightcraftmc.guildwars.java.util.classes.GameClass;
import com.lightcraftmc.guildwars.java.util.classes.UtilClass;
import com.lightcraftmc.guildwars.java.util.schedule.UtilSchedule;

public class ClickEntityEvent implements Listener {

    @EventHandler
    public void interact(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (!(e.getRightClicked() instanceof LivingEntity)) {
            return;
        }
        LivingEntity en = (LivingEntity) e.getRightClicked();
        if (en.getCustomName() == null) {
            return;
        }
        String s = ChatColor.stripColor(en.getCustomName());
        for (GameClass c : GameClass.values()) {
            if (s.equalsIgnoreCase(c.toString())) {
                p.sendMessage("§a§lYou are now playing as: " + c.getColor() + WordUtils.capitalize(c.toString().toLowerCase()));
                UtilClass.setClass(c, p);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 0);
                UtilSchedule.scheduleSync(new Runnable() {
                    public void run() {
                        GWScoreboard.setupScoreboard(p);
                    }
                }, 5);
                e.setCancelled(true);
                return;
            }
        }
    }

}
