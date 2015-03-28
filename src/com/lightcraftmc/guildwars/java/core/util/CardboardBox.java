package com.lightcraftmc.guildwars.java.core.util;

import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * A serializable ItemStack
 */
public class CardboardBox {

    private String type;
    private int amount;
    private byte data;
    private short durability;
    private String displayName;
    private ArrayList<String> lore = new ArrayList<String>();
    private String skullOwner = "none";
    private int color = 0;

    @SuppressWarnings("deprecation")
    public CardboardBox(ItemStack i) {
        type = i.getType().toString();
        amount = i.getAmount();
        data = i.getData().getData();
        durability = i.getDurability();
        if (i.hasItemMeta()) {
            if (i.getItemMeta().hasDisplayName()) {
                displayName = i.getItemMeta().getDisplayName().replace(",", "[comma]").replace("!", "[excl]");
            } else {
                displayName = WordUtils.capitalize(type.replace("_", " "));
            }
            if (i.getItemMeta().hasLore()) {
                for (String s : i.getItemMeta().getLore()) {
                    lore.add(s.replace(",", "[comma]").replace("!", "[excl]").replace("?", "[quest]"));
                }
            }
            try {
                if (i.getItemMeta() instanceof SkullMeta) {
                    SkullMeta m = (SkullMeta) i.getItemMeta();
                    skullOwner = m.getOwner();
                }
            } catch (Exception ex) {
            }

            try {
                if (i.getItemMeta() instanceof LeatherArmorMeta) {
                    LeatherArmorMeta m = (LeatherArmorMeta) i.getItemMeta();
                    color = m.getColor().asRGB();
                }
            } catch (Exception ex) {
            }
        }
    }

    public CardboardBox(String s) {
        // return toString(new String[] { type, amount + "", data + "",
        // durability + "", displayName, skullOwner, toString(lore, "?") },
        // "!");

        String[] a = s.split("!");
        try {
            type = a[0];
        } catch (Exception ex) {
        }
        try {
            amount = Integer.parseInt(a[1]);
        } catch (Exception ex) {
        }
        try {
            data = Byte.parseByte(a[2]);
        } catch (Exception ex) {
        }
        try {
            durability = Short.parseShort(a[3]);
        } catch (Exception ex) {
        }
        try {
            displayName = a[4];
        } catch (Exception ex) {
        }
        try {
            skullOwner = a[5];
        } catch (Exception ex) {
        }
        try {
            for (String s2 : a[6].split("?")) {
                lore.add(s2.replace("[comma]", ",").replace("[excl]", "!").replace("[quest]", "?"));
            }
        } catch (Exception ex) {
        }
        try {
            color = Integer.parseInt(a[7]);
        } catch (Exception ex) {
        }
    }

    public ItemStack getStack() {
        Material m = Material.STONE;
        try {
            for (Material mm : Material.values()) {
                if (mm.toString().equalsIgnoreCase(type)) {
                    m = mm;
                }
            }
        } catch (Exception ex) {
        }
        ItemStack i = new ItemStack(m, amount, durability, data);
        try {
            if (i.getType().equals(Material.SKULL) || i.getType().equals(Material.SKULL_ITEM)) {
                try {
                    SkullMeta meta = (SkullMeta) i.getItemMeta();
                    meta.setOwner(skullOwner);
                    i.setItemMeta(meta);
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex) {
        }
        try {
            if (i.getType().equals(Material.LEATHER_BOOTS) || i.getType().equals(Material.LEATHER_CHESTPLATE) || i.getType().equals(Material.LEATHER_HELMET)
                    || i.getType().equals(Material.LEATHER_LEGGINGS)) {
                try {
                    LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
                    meta.setColor(Color.fromRGB(color));
                    i.setItemMeta(meta);
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex) {
        }
        try {
            ItemMeta meta = i.getItemMeta();
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            i.setItemMeta(meta);
        } catch (Exception ex) {
        }
        return i;
    }

    public String toString(String[] obs, String rejex) {
        boolean first = true;
        String build = "";
        for (String obj : obs) {
            if (first) {
                build = obj;
                first = false;
            } else {
                build = build + rejex + obj;
            }
        }
        return build;
    }

    public String toString(ArrayList<String> obs, String rejex) {
        boolean first = true;
        String build = "";
        for (String obj : obs) {
            if (first) {
                build = obj;
                first = false;
            } else {
                build = build + rejex + obj;
            }
        }
        return build;
    }

    public String toString() {
        return toString(new String[] { type, amount + "", data + "", durability + "", displayName, skullOwner, toString(lore, "?"), color + "" }, "!");
    }

}