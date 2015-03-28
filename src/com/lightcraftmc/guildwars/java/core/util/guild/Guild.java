package com.lightcraftmc.guildwars.java.core.util.guild;

public enum Guild {

    RED("c", "RED"), GREEN("b", "GRN"), BLUE("3", "BLU"), YELLOW("e", "YEL");

    private final String toString;

    private final String trimName;

    private Guild(String s, String trimName) {
        toString = "§" + s;
        this.trimName = trimName;
    }

    public String getColor() {
        return toString;
    }

    public String getTrimName() {
        return trimName;
    }

}
