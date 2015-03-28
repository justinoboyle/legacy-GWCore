package com.lightcraftmc.guildwars.java.util.classes;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.main.GWCore;
import com.lightcraftmc.guildwars.java.util.schedule.UtilSchedule;

public class UtilClass {

    private static HashMap<String, String> savedClass = new HashMap<String, String>();

    public static void setClass(String cla, String uuid) {
        UtilSchedule.scheduleAsync(new Runnable() {
            public void run() {
                GWCore.getInstance().getDBManager().query("insert players/class " + uuid + " " + cla);
                UtilSchedule.scheduleSync(new Runnable() {
                    public void run() {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getUniqueId().toString().equalsIgnoreCase(uuid)) {
                                sync(p);
                            }
                        }
                    }
                });
            }
        });
    }

    public static void setClass(String cla, OfflinePlayer p) {
        setClass(cla, p.getUniqueId().toString());
    }

    public static void setClass(GameClass cla, OfflinePlayer p) {
        setClass(cla.toString(), p);
    }

    public static void setupRunnable() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GWCore.getInstance(), new Runnable() {
            public void run() {
                sync();
            }
        }, 0, 20 * 10);
    }

    private static void sync() {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (savedClass.containsKey(p.getUniqueId().toString()) && !p.isOnline()) {
                savedClass.remove(p.getUniqueId().toString());
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            String uuid = p.getUniqueId().toString();
            UtilSchedule.scheduleAsync(new Runnable() {
                public void run() {
                    UtilClass.getClass(uuid);
                }
            });
        }
    }

    public static void sync(Player p) {
        String uuid = p.getUniqueId().toString();
        UtilSchedule.scheduleAsync(new Runnable() {
            public void run() {
                UtilClass.getClass(uuid);
            }
        });
    }

    public static void disconnect(String uuid) {
        if (savedClass.containsKey(uuid)) {
            savedClass.remove(uuid);
        }
    }

    @Deprecated
    public static String getClass(String uuid) {
        String query = GWCore.getInstance().getDBManager().query("retrieve players/class " + uuid);
        if (savedClass.containsKey(uuid)) {
            savedClass.remove(uuid);
        }
        savedClass.put(uuid, query);
        return query;
    }

    public static GameClass getClassSafely(String uuid) {
        if (savedClass.containsKey(uuid)) {
            String s = savedClass.get(uuid);
            for (GameClass c : GameClass.values()) {
                if (s.equalsIgnoreCase(c.toString())) {
                    return c;
                }
            }
        }
        return GameClass.BERSERKER;
    }

    public static GameClass getClassFromNameString(String s) {
        for (GameClass c : GameClass.values()) {
            if (s.equalsIgnoreCase(c.toString())) {
                return c;
            }
        }
        return GameClass.BERSERKER;
    }

}
