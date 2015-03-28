package com.lightcraftmc.guildwars.java.util.classes;

public enum GameClass {

    BERSERKER("6", "B"), RANGER("6", "R"), HEALER("6", "H");

    private final String toString;

    private final String trimName;

    private GameClass(String s, String trimName) {
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
