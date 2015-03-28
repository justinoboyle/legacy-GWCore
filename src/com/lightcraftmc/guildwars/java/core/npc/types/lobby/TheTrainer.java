package com.lightcraftmc.guildwars.java.core.npc.types.lobby;

import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.npc.NPC;

public class TheTrainer extends NPC {

    @Override
    public String getExactName() {
        return "The Trainer";
    }

    @Override
    public void interact(Player p, LivingEntity e) {
        talk(p, NPC.NOT_READY);
        p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 0);
        p.setVelocity(p.getLocation().getDirection().multiply(-2.3D).setY(0.4));

    }

    @Override
    public boolean isCurrentlyInBeta() {
        return true;
    }

}
