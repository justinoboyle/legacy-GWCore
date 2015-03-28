package com.lightcraftmc.guildwars.java.core.npc.types.lobby;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.npc.NPC;
import com.lightcraftmc.guildwars.java.core.util.UtilSpawnItems;

public class VIPLoungeExit extends NPC {

    @Override
    public String getExactName() {
        return "Exit the Lounge";
    }

    String[] welcome = new String[] { "Sad to see you go!", "See you later, %name%!", "Was nice to see you, %name%!", "Catch you later, %name%!" };

    @SuppressWarnings("deprecation")
    @Override
    public void interact(Player p, LivingEntity e) {
        this.talk(p, welcome[new Random().nextInt(welcome.length - 1)].replace("%name%", p.getName()));
        p.teleport(new Location(p.getWorld(), 14, 107, -11, -135, 10));
        p.playEffect(p.getLocation(), Effect.PORTAL, 1);
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
        UtilSpawnItems.giveItems(p);
        
    }

    @Override
    public boolean isCurrentlyInBeta() {
        return false;
    }

}
