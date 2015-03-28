package com.lightcraftmc.guildwars.java.core.listener.world;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.lightcraftmc.guildwars.java.core.main.GWCore;
import com.lightcraftmc.guildwars.java.core.util.BUtils;
import com.lightcraftmc.guildwars.java.util.schedule.UtilSchedule;

public class LaunchPadEvent implements Listener {

    ArrayList<UUID> cooldown = new ArrayList<UUID>();
    ArrayList<UUID> wait = new ArrayList<UUID>();

    public LaunchPadEvent(){
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GWCore.getInstance(), new Runnable(){
            public void run(){
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR){
                       // BUtils.sendActionBar(p, "§cHint:§4 You can press Q while holding the book to learn about things!");
                    }
                }
            }
        }, 0, 20*10);
    }
    
    
    @EventHandler
    public void drop(PlayerDropItemEvent e){
        e.setCancelled(true);
        Player p = e.getPlayer();
        
        Block b = e.getPlayer().getTargetBlock(null, 300);
        UtilSchedule.scheduleSync(new Runnable(){
            public void run(){
                e.getPlayer().updateInventory();
            }
        });
        if(!e.getItemDrop().getItemStack().getType().equals(Material.BOOK)) return;
        if(b.getType().equals(Material.LEAVES) || b.getType().equals(Material.LOG)){
            p.sendMessage("§bInfo > §7That's a tree.");
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
            return;
        }
        if(b.getType().toString().contains("SIGN")){
            p.sendMessage("§bInfo > §7That is a sign, which can carry important text that you can read.");
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
            return;
        }
        p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 0);
        BUtils.sendActionBar(p, "§4§o§lNothing can be learned here.");
    }
    
    @EventHandler
    public void interact2(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().toString().contains("CLICK")) {
            if (p.getItemInHand().getType().equals(Material.BLAZE_ROD) && !cooldown.contains(p.getUniqueId()) && !p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
                pullEntityToLocation(p, p.getTargetBlock(null, 50).getLocation());
                p.playEffect(p.getLocation(), Effect.SMOKE, 15);
                p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
                cooldown.add(p.getUniqueId());
                UtilSchedule.scheduleSync(new Runnable() {
                    public void run() {
                        cooldown.remove(p.getUniqueId());
                    }
                }, 10);
            }
        }
        if (!e.getAction().equals(Action.PHYSICAL))
            return;

        if (e.getPlayer().getLocation().getBlock().getType().equals(Material.IRON_PLATE) && e.getPlayer().getLocation().add(0, -1, 0).getBlock().getType().equals(Material.REDSTONE_BLOCK)) {
            p.playEffect(p.getLocation(), Effect.SMOKE, 15);
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
            p.setVelocity(p.getLocation().getDirection().multiply(3D).setY(1));
        }

    }

    @EventHandler
    public void move(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        p.setFallDistance(0f);
        if(p.getLocation().getY() < 0){
            p.teleport(p.getWorld().getSpawnLocation());
            p.setVelocity(new Vector(0, 0, 0));
        }
        if (e.getPlayer().getLocation().getBlock().getType().equals(Material.IRON_PLATE) && e.getPlayer().getLocation().add(0, -1, 0).getBlock().getType().equals(Material.REDSTONE_BLOCK)) {
            p.playEffect(p.getLocation(), Effect.SMOKE, 15);
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
            p.setVelocity(p.getLocation().getDirection().multiply(3D).setY(1));
        }
    }

    private void pullEntityToLocation(final Entity e, Location loc) {
        Location entityLoc = e.getLocation();

        entityLoc.setY(entityLoc.getY() + 0.5);
        e.teleport(entityLoc);

        double g = -0.08;
        double d = loc.distance(entityLoc);
        double t = d;
        double v_x = (1.0 + 0.07 * t) * (loc.getX() - entityLoc.getX()) / t;
        double v_y = (1.0 + 0.03 * t) * (loc.getY() - entityLoc.getY()) / t - 0.5 * g * t;
        double v_z = (1.0 + 0.07 * t) * (loc.getZ() - entityLoc.getZ()) / t;

        Vector v = e.getVelocity();
        v.setX(v_x);
        if(v_y > 1.2){
            v_y = 1.2;
        }
        v.setY(v_y);
        v.setZ(v_z);
        e.setVelocity(v);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.getCause() == DamageCause.FALL) {
            event.setCancelled(true);
            if(event.getEntity() instanceof Player){
                Player p = (Player) event.getEntity();
                p.setFallDistance(0f);
                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 0);
            }
        }
    }

}
