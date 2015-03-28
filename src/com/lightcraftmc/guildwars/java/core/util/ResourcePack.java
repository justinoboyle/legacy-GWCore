package com.lightcraftmc.guildwars.java.core.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class ResourcePack {

    public static boolean hasResourcePack(OfflinePlayer p) {
        if (!Bukkit.getPluginManager().isPluginEnabled("GWResources")) {
            return true;
        }
        if (p.getName().equalsIgnoreCase("ArrayPro")) {
            return true;
        }
        return true;
        //return com.lightcraftmc.gw.GWResources.hasAcceptedPlayer(p);

    }

}
