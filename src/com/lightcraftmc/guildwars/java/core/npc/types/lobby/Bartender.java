package com.lightcraftmc.guildwars.java.core.npc.types.lobby;

import mc.lightcraft.java.local.util.ItemUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.lightcraftmc.guildwars.java.core.npc.NPC;

public class Bartender extends NPC {

    @Override
    public String getExactName() {
        return "Bartender";
    }

    @Override
    public void interact(Player p, LivingEntity e) {
        talk(p, "Here's what I have!");
        p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 2);
        ItemStack[] list = new ItemStack[] {
                ItemUtils.setName(new ItemStack(Material.POTION, 1, (short) 8193), "§aBlueberi Alias"),
                ItemUtils.setName(new ItemStack(Material.POTION, 1, (short) 8227), "§aAngler's Cocktail"),
                ItemUtils.setName(new ItemStack(Material.POTION, 1, (short) 8268), "§aEspresso Martini"),
                ItemUtils.setName(new ItemStack(Material.POTION, 1, (short) 8230), "§aBlue Ice"),
                ItemUtils.setName(new ItemStack(Material.POTION, 1, (short) 8196), "§aGreen Bear"),
                ItemUtils.setName(new ItemStack(Material.POTION, 1, (short) 8226), "§aBlue Lagoon"),
                ItemUtils.setName(new ItemStack(Material.POTION, 1, (short) 8203), "§aBlue Hawaiian"),
                
        };
        Inventory i = Bukkit.createInventory(null, 9*3, "Bartender");
        int ii = 10;
        for(ItemStack it : list){
            i.setItem(ii, it);
            ii++;
        }
        p.openInventory(i);
        
    }

    @Override
    public boolean isCurrentlyInBeta() {
        return true;
    }

}
    