package com.lightcraftmc.guildwars.java.core.soundspots.manager;

import java.util.ArrayList;

import com.lightcraftmc.guildwars.java.core.main.GWCore;
import com.lightcraftmc.guildwars.java.core.soundspots.SoundSpot;
import com.lightcraftmc.guildwars.java.core.soundspots.list.SoundSpotLounge;

public class SoundSpotManager {

    private static ArrayList<SoundSpot> spots = new ArrayList<SoundSpot>();

    public static void registerSoundSpot(SoundSpot s) {
        if (!spots.contains(s)) {
            spots.add(s);
        }
    }

    public static ArrayList<SoundSpot> getSoundSpotList() {
        return spots;
    }

    public static void setup() {
        if(GWCore.getInstance().getConfig().getString("server").toLowerCase().contains("lobby")){
            spots.add(new SoundSpotLounge());
        }
    }
    
}
