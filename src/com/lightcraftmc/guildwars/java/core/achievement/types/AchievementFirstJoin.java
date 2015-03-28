package com.lightcraftmc.guildwars.java.core.achievement.types;

import com.lightcraftmc.guildwars.java.core.achievement.abs.Achievement;

public class AchievementFirstJoin extends Achievement {

    @Override
    public String getName() {
        return "firstjoin";
    }

    @Override
    public boolean shouldShootFireworks() {
        return true;
    }

    @Override
    public boolean shouldAnnounceInChat() {
        return false;
    }

    @Override
    public boolean hasPrerequisites(String p) {
        return true;
    }

    @Override
    public String getDisplayName() {
        return "Welcome to the server!";
    }

}
