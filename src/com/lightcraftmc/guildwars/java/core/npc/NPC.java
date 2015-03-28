package com.lightcraftmc.guildwars.java.core.npc;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class NPC implements Listener {

    public static final String NOT_READY = "Sorry, but I'm not available yet! Check back later!";

    public NPC() {
        NPCList.registerNPC(this);
    }

    public abstract String getExactName();

    public abstract void interact(Player p, LivingEntity e);

    public abstract boolean isCurrentlyInBeta();

    public void talk(Player p, String message) {
        NPC.talk(p, message, getExactName());
    }

    public static void talk(Player p, String message, String npcName) {
        p.sendMessage("§2" + npcName + ": §7" + message);
    }

}
