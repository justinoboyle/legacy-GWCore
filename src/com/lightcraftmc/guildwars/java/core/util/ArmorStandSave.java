package com.lightcraftmc.guildwars.java.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import com.lightcraftmc.guildwars.java.util.schedule.UtilSchedule;

public class ArmorStandSave {

    public static void saveStands() {
        final ArrayList<String> stands = new ArrayList<String>();
        for (World w : Bukkit.getWorlds())
            for (Entity e : w.getEntities()) {
                if (e instanceof ArmorStand) {
                    ArmorStand a = (ArmorStand) e;
                    if (!(a.getCustomName() != null && a.getCustomName().equals("fish"))) {
                        SerializableArmorStand s = new SerializableArmorStand(a);
                        stands.add(s.toString());
                    }
                }
            }
        UtilSchedule.scheduleAsync(new Runnable() {
            public void run() {
                try {
                    save("stands.lcdb", stands);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void loadStands() {
        final ArrayList<String> stands = new ArrayList<String>();
        try {
            stands.addAll(read("stands.lcdb"));
            for (String s : stands) {
                SerializableArmorStand a = new SerializableArmorStand(s);
                a.summonArmorStand();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void save(String fileName, ArrayList<String> stands) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(stands);
        oos.close();
    }

    public static ArrayList<String> read(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<String> n = (ArrayList<String>) ois.readObject();
        ois.close();
        return n;
    }

}
