package com.lightcraftmc.guildwars.java.core.util;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class BoxedLocation implements Serializable {
    private static final long serialVersionUID = -3939940323003538249L;
    double x;
    double y;
    double z;
    String worldName;

    public BoxedLocation(Location loc) {
        x = loc.getX();
        y = loc.getY();
        z = loc.getZ();
        worldName = loc.getWorld().getName();
    }

    public BoxedLocation(String s) {
        String[] ss = s.split("!");
        worldName = ss[0];
        x = Double.parseDouble(ss[1]);
        y = Double.parseDouble(ss[2]);
        z = Double.parseDouble(ss[3]);
    }

    public Location unbox() {
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    public boolean equals(Object b) {
        if (!(b instanceof BoxedLocation)) {
            return false;
        }
        BoxedLocation bl = (BoxedLocation) b;

        return (x == x) && (y == y) && (z == z) && (worldName.equals(worldName));
    }

    public String toString() {
        return worldName + "!" + x + "!" + y + "!" + z;
    }
}
