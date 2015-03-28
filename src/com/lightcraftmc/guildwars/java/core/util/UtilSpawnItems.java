package com.lightcraftmc.guildwars.java.core.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class UtilSpawnItems {
    
    public static void giveItems(Player p){
        p.getInventory().clear();
        for(PotionEffect e : p.getActivePotionEffects()){
            p.removePotionEffect(e.getType());
        }
        p.updateInventory();
    }

}
