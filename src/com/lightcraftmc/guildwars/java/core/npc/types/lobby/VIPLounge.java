package com.lightcraftmc.guildwars.java.core.npc.types.lobby;

import java.util.Random;

import mc.lightcraft.java.common.rank.tree.RankTree;
import mc.lightcraft.java.common.rank.tree.ServerRank;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.npc.NPC;

public class VIPLounge extends NPC {

    @Override
    public String getExactName() {
        return "VIP Lounge";
    }

    String[] welcome = new String[] { "Welcome to the club!", "Welcome, %name%", "Nice to see you, %name%!", "Hey, %name%, catch you later!" };

    @SuppressWarnings("deprecation")
    @Override
    public void interact(Player p, LivingEntity e) {
        if (!RankTree.getTree().hasRank(RankTree.getTree().getRank(p), ServerRank.VIP)) {
            this.talk(p, "You're not a VIP! You can't enter here!");
            p.sendMessage("§c§lYou can buy VIP on the store!");
            p.sendMessage("§bhttp://store.lightcraftmc.com/");
            p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 0);
            p.setVelocity(p.getLocation().getDirection().multiply(-2.3D).setY(0.4));
            return;
        }

        this.talk(p, welcome[new Random().nextInt(welcome.length - 1)].replace("%name%", p.getName()));
        p.teleport(new Location(p.getWorld(), 6, 80, 44, 135, 0));
        p.playEffect(p.getLocation(), Effect.PORTAL, 1);
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
        p.getInventory().clear();
        p.updateInventory();
        
    }

    @Override
    public boolean isCurrentlyInBeta() {
        return false;
    }

}
