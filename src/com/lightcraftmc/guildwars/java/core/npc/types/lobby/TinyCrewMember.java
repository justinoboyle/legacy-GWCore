package com.lightcraftmc.guildwars.java.core.npc.types.lobby;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.npc.NPC;

public class TinyCrewMember extends NPC {

    private final Random rand = new Random();

    @Override
    public String getExactName() {
        return "Tiny Crew Member";
    }

    @Override
    public void interact(Player p, LivingEntity e) {
        this.talk(p, pickQuote());
        p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 2, 0);
    }

    @Override
    public boolean isCurrentlyInBeta() {
        return false;
    }

    public String pickQuote() {
        return "I am a midget.";
    }

}
