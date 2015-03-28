package com.lightcraftmc.guildwars.java.core.soundspots;

import org.bukkit.Location;

public abstract class SoundSpot {

    public abstract String getName();

    public abstract Location getLocation();

    public abstract String getSoundName();

    public abstract int getRefreshTicks();

}
