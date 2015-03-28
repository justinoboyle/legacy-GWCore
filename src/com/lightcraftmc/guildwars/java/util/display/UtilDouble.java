package com.lightcraftmc.guildwars.java.util.display;

public class UtilDouble {

    public static String getString(double d){
        if(d % 1 == 0){
            return "" + (int)d;
        }
        return d + "";
    }
}
