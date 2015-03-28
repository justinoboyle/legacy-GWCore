package com.lightcraftmc.guildwars.java.core.npc;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import com.lightcraftmc.guildwars.java.core.main.GWCore;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.ArenaManager;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.Bartender;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.CrewMember;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.PotionsDealer;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.RandomInsightGuy;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.Randy;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.TheBlacksmith;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.TheTrainer;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.TinyCrewMember;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.VIPLounge;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.VIPLoungeExit;
import com.lightcraftmc.guildwars.java.core.npc.types.lobby.XPTrader;

public class NPCList {

    private static ArrayList<NPC> displayNames = new ArrayList<NPC>();

    public static void registerNPC(NPC s) {
        if (!displayNames.contains(s)) {
            displayNames.add(s);
            Bukkit.getServer().getPluginManager().registerEvents(s, GWCore.getInstance());
        }
    }

    public static ArrayList<NPC> getNPCList() {
        return displayNames;
    }

    public static void setup() {
        new TheTrainer();
        new TheBlacksmith();
        new ArenaManager();
        new PotionsDealer();
        new XPTrader();
        
        new CrewMember();
        new TinyCrewMember();
        
        new Randy();
        
        new Bartender();
        new VIPLoungeExit();
        new VIPLounge();
        new RandomInsightGuy();
        
    }

}
