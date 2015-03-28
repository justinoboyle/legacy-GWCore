package com.lightcraftmc.guildwars.java.core.util.calculation;

import java.util.HashMap;

public class UtilCalculation {

    private static HashMap<Double, Long> alreadyCalculated = new HashMap<Double, Long>();
    private static HashMap<Double, Double> alreadyCalculatedMultiplier = new HashMap<Double, Double>();

    private static void calculateLevels() {
        double d = 0;
        while (d <= 126) {
            getLevel(d * 1000);
            d++;
        }
    }

    public static long getLevel(double exp) {
        if (alreadyCalculated.containsKey(exp)) {
            return alreadyCalculated.get(exp);
        }
        double w = 0.001 * exp / Math.PI * 2.5;
        if (w > 100) {
            return 100;
        }
        long ret = Math.round(w);
        alreadyCalculated.put(exp, ret);
        return ret;
    }

    public static double getMultiplier(double level) {
        if (alreadyCalculatedMultiplier.containsKey(level)) {
            return alreadyCalculatedMultiplier.get(level);
        }
        double work = (Math.round(level / 20) * 4.5) - 1;
        if (work == 21.5) {
            return 25;
        }
        if (work < 1) {
            return 1.0;
        }
        alreadyCalculatedMultiplier.put(level, work);
        return work;
    }

}
