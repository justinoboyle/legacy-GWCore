package com.lightcraftmc.guildwars.java.core.soundspots.list;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.lightcraftmc.guildwars.java.core.soundspots.SoundSpot;

public class SoundSpotLounge extends SoundSpot {

    @Override
    public String getName() {
        return "lounge";
    }

    @Override
    public Location getLocation() {
        return new Location(Bukkit.getWorld("GuildWars_HUB02"), -2, 80, 46);
    }

    @Override
    public String getSoundName() {
        return "music_lounge_jazz";
    }

    @Override
    public int getRefreshTicks() {
        return 45 * 20;
    }

}
