package com.lightcraftmc.guildwars.java.core.achievement.abs;

import org.bukkit.event.Listener;

public abstract class Achievement implements Listener {

    public abstract String getName();
    
    public abstract String getDisplayName();

    public abstract boolean shouldShootFireworks();

    public abstract boolean shouldAnnounceInChat();

    public boolean hasAchievement(String p) {
        return false;
    }

    public abstract boolean hasPrerequisites(String p);

}
