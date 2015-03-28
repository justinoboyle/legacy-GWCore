package com.lightcraftmc.guildwars.java.core.util.bungee;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeCord {

    public static void query(String[] message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        for (String s : message)
            out.writeUTF(s);

        Player player = null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            player = p;
            break;
        }
        if (player == null) {
            System.out.println("[ERROR] No players are online, so BC query was not sent.");
        }
    }

}
