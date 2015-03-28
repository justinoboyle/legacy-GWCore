package com.lightcraftmc.guildwars.java.util.schedule;

import org.bukkit.Bukkit;

import com.lightcraftmc.guildwars.java.core.main.GWCore;

public class UtilSchedule {

    @SuppressWarnings("deprecation")
    public static void scheduleAsync(Runnable r, int interval) {
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), r, interval);
    }

    public static void scheduleAsync(Runnable r) {
        scheduleAsync(r, 1);
    }

    public static void scheduleSync(Runnable r, int interval) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), r, interval);
    }

    public static void scheduleSync(Runnable r) {
        scheduleSync(r, 1);
    }

}
