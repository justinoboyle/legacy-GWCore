package com.lightcraftmc.guildwars.java.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class SerializableArmorStand {

    private String bodyPose;
    private String leftArmPose;
    private String rightArmPose;
    private String leftLegPose;
    private String rightLegPose;
    private String headPose;

    private boolean hasBasePlate;
    private boolean hasGravity;
    private boolean visible;
    private boolean arms;
    private boolean small;

    private String location;

    private String customName = "NOT_NAMED";

    private String itemInHand;
    private String itemHelmet;
    private String itemChestplate;
    private String itemLeggings;
    private String itemBoots;

    private float yaw;
    private float pitch;

    public ArmorStand summonArmorStand() {
        if (customName == null) {
            customName = "NOT_NAMED";
        }
        Location loc = new BoxedLocation(location).unbox();
        ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
        try {
            a.setBodyPose(SerializableEulerAngle.fromString(bodyPose));
        } catch (Exception ex) {
        }
        try {
            a.setLeftArmPose(SerializableEulerAngle.fromString(leftArmPose));
        } catch (Exception ex) {
        }
        try {
            a.setRightArmPose(SerializableEulerAngle.fromString(rightArmPose));
        } catch (Exception ex) {
        }
        try {
            a.setLeftLegPose(SerializableEulerAngle.fromString(leftLegPose));
        } catch (Exception ex) {
        }
        try {
            a.setRightLegPose(SerializableEulerAngle.fromString(rightLegPose));
        } catch (Exception ex) {
        }
        try {
            a.setHeadPose(SerializableEulerAngle.fromString(headPose));
        } catch (Exception ex) {
        }
        try {
            if (customName != null && !customName.equals("NOT_NAMED") && !customName.equals("null")) {
                a.setCustomName(customName);
                a.setCustomNameVisible(true);
            } else {
                a.setCustomName(null);
                a.setCustomNameVisible(false);
            }
        } catch (Exception ex) {
        }
        try {
            a.setBasePlate(hasBasePlate);
        } catch (Exception ex) {
        }
        try {
            a.setGravity(hasGravity);
        } catch (Exception ex) {
        }
        try {
            a.setVisible(visible);
        } catch (Exception ex) {
        }
        try {
            a.setArms(arms);
        } catch (Exception ex) {
        }
        try {
            a.setSmall(small);
        } catch (Exception ex) {
        }
        try {
            a.setItemInHand(new CardboardBox(itemInHand).getStack());
        } catch (Exception ex) {
        }
        try {
            a.setHelmet(new CardboardBox(itemHelmet).getStack());
        } catch (Exception ex) {
        }
        try {
            a.setChestplate(new CardboardBox(itemChestplate).getStack());
        } catch (Exception ex) {
        }
        try {
            a.setLeggings(new CardboardBox(itemLeggings).getStack());
        } catch (Exception ex) {
        }
        try {
            a.setBoots(new CardboardBox(itemBoots).getStack());
        } catch (Exception ex) {
        }
        try {
            Location loc2 = a.getLocation();
            loc2.setYaw(yaw);
            a.teleport(loc2);
        } catch (Exception ex) {
        }
        try {
            Location loc2 = a.getLocation();
            loc2.setPitch(pitch);
            a.teleport(loc2);
        } catch (Exception ex) {
        }
        return a;
    }

    public SerializableArmorStand(ArmorStand a) {

        if (customName == null) {
            customName = "NOT_NAMED";
        }

        bodyPose = SerializableEulerAngle.toString(a.getBodyPose());
        leftArmPose = SerializableEulerAngle.toString(a.getLeftArmPose());
        rightArmPose = SerializableEulerAngle.toString(a.getRightArmPose());
        leftLegPose = SerializableEulerAngle.toString(a.getLeftLegPose());
        rightLegPose = SerializableEulerAngle.toString(a.getRightLegPose());
        headPose = SerializableEulerAngle.toString(a.getHeadPose());

        hasBasePlate = a.hasBasePlate();
        hasGravity = a.hasGravity();
        visible = a.isVisible();
        arms = a.hasArms();
        small = a.isSmall();

        try {
            if (a.getCustomName() != null && a.isCustomNameVisible()) {
                customName = a.getCustomName();
            } else {
                customName = "NOT_NAMED";
            }
        } catch (Exception ex) {
        }

        location = new BoxedLocation(a.getLocation()).toString();

        itemInHand = new CardboardBox(a.getItemInHand()).toString();
        itemHelmet = new CardboardBox(a.getHelmet()).toString();
        itemChestplate = new CardboardBox(a.getChestplate()).toString();
        itemLeggings = new CardboardBox(a.getLeggings()).toString();
        itemBoots = new CardboardBox(a.getBoots()).toString();

        yaw = a.getLocation().getYaw();
        pitch = a.getLocation().getPitch();

        if (customName == null) {
            customName = "NOT_NAMED";
        }

    }

    public SerializableArmorStand(String s) {
        String[] a = s.split(",");

        bodyPose = a[0];
        leftArmPose = a[1];
        rightArmPose = a[2];
        leftLegPose = a[3];
        rightLegPose = a[4];
        headPose = a[5];

        hasBasePlate = Boolean.parseBoolean(a[6]);
        hasGravity = Boolean.parseBoolean(a[7]);
        visible = Boolean.parseBoolean(a[8]);
        arms = Boolean.parseBoolean(a[9]);
        small = Boolean.parseBoolean(a[10]);

        location = a[11];

        try {
            customName = a[12];
        } catch (Exception ex) {
        }

        itemInHand = a[13];
        itemHelmet = a[14];
        itemChestplate = a[15];
        itemLeggings = a[16];
        itemBoots = a[17];

        try {
            yaw = Float.parseFloat(a[18]);
        } catch (Exception ex) {
        }
        try {
            pitch = Float.parseFloat(a[19]);
        } catch (Exception ex) {
        }

        if (customName == null) {
            customName = "NOT_NAMED";
        }
    }

    public String toString(String[] obs) {
        boolean first = true;
        String build = "";
        for (String obj : obs) {
            if (first) {
                build = obj;
                first = false;
            } else {
                build = build + "," + obj;
            }
        }
        return build;
    }

    public String toString() {
        if (customName == null) {
            customName = "NOT_NAMED";
        }
        return toString(new String[] { bodyPose, leftArmPose, rightArmPose, leftLegPose, rightLegPose, headPose, hasBasePlate + "", hasGravity + "", visible + "", arms + "", small + "", location,
                customName, itemInHand, itemHelmet, itemChestplate, itemLeggings, itemBoots, yaw + "", pitch + "" });
    }

}
