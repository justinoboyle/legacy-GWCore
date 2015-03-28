package com.lightcraftmc.guildwars.java.core.util;

import org.bukkit.util.EulerAngle;

public class SerializableEulerAngle {
    public static String toString(EulerAngle a) {
        return a.getX() + "!" + a.getY() + "!" + a.getZ();
    }

    public static EulerAngle fromString(String s) {
        String[] w = s.split("!");
        return new EulerAngle(Double.parseDouble(w[0]), Double.parseDouble(w[1]), Double.parseDouble(w[2]));
    }

}
