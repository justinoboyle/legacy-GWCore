package com.lightcraftmc.guildwars.java.core.util.multiplier;

import java.util.ArrayList;

public class UtilMultiplier {

    public static double getAmount(ArrayList<Multiplier> multipliers) {
        double current = 1;
        for (Multiplier m : multipliers) {
            current = current + m.getAmount();
        }
        return current;
    }

    public static double addMultipliers(double amount, ArrayList<Multiplier> multipliers) {
        return amount * getAmount(multipliers);
    }

    public static String generateBuiltNameString(ArrayList<Multiplier> multipliers) {
        String s = "";
        boolean start = true;
        for (Multiplier m : multipliers) {
            if (m.isNameVisible()) {
                if (start) {
                    s = m.getDisplayName();
                    start = false;
                } else {
                    s = s + ", " + m.getDisplayName();
                }
            }
        }
        return s;
    }

    public static String generateGiveString(double experience, String builtNameString, String currency, String currencyColor) {
        return currencyColor + "+" + experience + " " + currency + "!§7 (" + builtNameString + ")";
    }

    public static String generateRareCoinsString(double amount, String reason) {
        return generateGiveString(amount, reason, "Rare Coins", "§d");
    }
}
