package com.lightcraftmc.guildwars.java.core.currency;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.main.GWCore;
import com.lightcraftmc.guildwars.java.core.util.multiplier.Multiplier;
import com.lightcraftmc.guildwars.java.core.util.multiplier.UtilMultiplier;
import com.lightcraftmc.guildwars.java.util.schedule.UtilSchedule;

public abstract class Currency {

    private HashMap<String, Double> saved = new HashMap<String, Double>();

    private static ArrayList<Currency> currencies = new ArrayList<Currency>();

    public abstract String getDisplayName();

    public abstract String getFormatting();

    public abstract String getSaveName();

    public Currency(){
        currencies.add(this);
    }
    
    public static void init() {
        for (Currency c : currencies) {
            c.setupRunnable();
        }
    }

    public static void syncGlobal(Player p) {
        for (Currency c : currencies) {
            c.sync(p);
        }
    }

    public static void disconnectGlobal(String uuid) {
        for (Currency c : currencies) {
            c.disconnect(uuid);
        }
    }

    public void give(double amount, OfflinePlayer p) {
        String uuid = p.getUniqueId().toString();
        UtilSchedule.scheduleAsync(new Runnable() {
            public void run() {
                double currentAmount = get(uuid);
                currentAmount = currentAmount + amount;
                GWCore.getInstance().getDBManager().query("insert players/currency/" + getSaveName() + " " + uuid + " " + currentAmount);
                UtilSchedule.scheduleSync(new Runnable() {
                    public void run() {
                        if (p.isOnline()) {
                            sync(p.getPlayer());
                        }
                    }
                });
            }
        });
    }

    public void give(double amount, OfflinePlayer p, ArrayList<Multiplier> multipliers, String reason) {
        if (multipliers != null) {
            amount = UtilMultiplier.addMultipliers(amount, multipliers);
        }
        if (multipliers == null) {
            multipliers = new ArrayList<Multiplier>();
        }
        give(amount, p);

    }
    
    void setupRunnable() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GWCore.getInstance(), new Runnable() {
            public void run() {
                sync();
            }
        }, 0, 20 * 5);
    }

    private void sync() {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (saved.containsKey(p.getUniqueId().toString()) && !p.isOnline()) {
                saved.remove(p.getUniqueId().toString());
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            String uuid = p.getUniqueId().toString();
            UtilSchedule.scheduleAsync(new Runnable() {
                public void run() {
                    get(uuid);
                }
            });
        }
    }

    public void sync(Player p) {
        String uuid = p.getUniqueId().toString();
        UtilSchedule.scheduleAsync(new Runnable() {
            public void run() {
                get(uuid);
            }
        });
    }

    public void disconnect(String uuid) {
        if (saved.containsKey(uuid)) {
            saved.remove(uuid);
        }
    }

    @Deprecated
    private double get(String uuid) {
        String query = GWCore.getInstance().getDBManager().query("retrieve players/currency/" + getSaveName() + " " + uuid);
        double i = 0;
        try {
            i = Double.parseDouble(query);
        } catch (Exception ex) {
            i = 0;
        }
        if (saved.containsKey(uuid)) {
            saved.remove(uuid);
        }
        saved.put(uuid, i);
        return i;
    }

    public double getSafely(String uuid) {
        if (saved.containsKey(uuid)) {
            return saved.get(uuid);
        }
        return 0;
    }

}
