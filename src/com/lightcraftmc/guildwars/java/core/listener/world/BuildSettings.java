package com.lightcraftmc.guildwars.java.core.listener.world;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.lightcraftmc.guildwars.java.core.util.BUtils;
import com.lightcraftmc.guildwars.java.core.util.ResourcePack;

public class BuildSettings implements Listener {

    private static ArrayList<String> allowBlockBreak = new ArrayList<String>();
    private static boolean allowBreakCreative = true;
    static boolean disableHunger = true;

    Material[] blockPhysics = { Material.GRAVEL, Material.SAND, Material.GRASS, Material.COMMAND, Material.REDSTONE_TORCH_ON, Material.LEVER, Material.REDSTONE, Material.REDSTONE_WIRE,
            Material.REDSTONE_BLOCK };

    @EventHandler
    public void handleLeafDecay(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void hunger(FoodLevelChangeEvent event) {
        if (!disableHunger)
            return;
        Player player = (Player) event.getEntity();
        event.setCancelled(true);
        player.setFoodLevel(20);
    }

    @EventHandler
    public void handleBlockFade(BlockFadeEvent e) {
        for (Material m : blockPhysics) {
            if (e.getBlock().getType().equals(m)) {
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void handleBlockPhysics(BlockPhysicsEvent e) {
        for (Material m : blockPhysics) {
            if (e.getBlock().getType().equals(m)) {
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!ResourcePack.hasResourcePack(e.getPlayer())) {
            BUtils.sendActionBar(e.getPlayer(), "§c§lYou must accept our resource pack to play on Guild Wars.");
            e.setCancelled(true);
            return;
        }
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            if (allowBreakCreative) {
                return;
            }
        }
        if (allowBlockBreak.contains(e.getBlock().getWorld().getName())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!ResourcePack.hasResourcePack(e.getPlayer())) {
            BUtils.sendActionBar(e.getPlayer(), "§c§lYou must accept our resource pack to play on Guild Wars.");
            e.setCancelled(true);
            return;
        }
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            if (allowBreakCreative) {
                return;
            }
        }
        if (allowBlockBreak.contains(e.getBlock().getWorld().getName())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void move(PlayerMoveEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
        }
        if (!ResourcePack.hasResourcePack(e.getPlayer())) {
            BUtils.sendActionBar(e.getPlayer(), "§c§lYou must accept our resource pack to play on Guild Wars.");
            e.getPlayer().teleport(e.getFrom());
            return;
        }
    }

    /*
     * The following APIs are for adding worlds for players to break blocks.
     * This should also be used if you would like to handle the block
     * place/break events in your own plugin.
     */

    public static void allowBlockBreak(String worldName) {
        if (!allowBlockBreak.contains(worldName))
            allowBlockBreak.add(worldName);
    }

    public static void revokeBlockBreak(String worldName) {
        if (allowBlockBreak.contains(worldName))
            allowBlockBreak.remove(worldName);
    }

    public static void disableServerHunger() {
        disableHunger = true;
    }

    public static void enableServerHunger() {
        disableHunger = false;
    }

    public static boolean isHungerEnabled() {
        return !disableHunger;
    }

    public static boolean isHungerDisabled() {
        return disableHunger;
    }

}
