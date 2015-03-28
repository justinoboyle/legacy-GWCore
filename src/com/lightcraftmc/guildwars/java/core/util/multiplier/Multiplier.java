package com.lightcraftmc.guildwars.java.core.util.multiplier;
public class Multiplier {

    private MultiplierType type;
    private String owner;
    private double amount;
    private String displayName;
    private boolean nameVisible = true;

    public Multiplier(MultiplierType type, String owner, double amount, String displayName) {
        super();
        this.type = type;
        this.owner = owner;
        this.amount = amount;
        this.displayName = displayName;
    }

    public Multiplier(MultiplierType type, double amount, String displayName) {
        super();
        this.type = type;
        this.owner = "**SERVER**";
        this.amount = amount;
        this.displayName = displayName;
    }

    public Multiplier(MultiplierType type, double amount) {
        super();
        this.type = type;
        this.owner = "**SERVER**";
        this.amount = amount;
        this.displayName = amount + "x Multiplier";
    }

    public Multiplier(MultiplierType type, String owner, double amount) {
        super();
        this.type = type;
        this.owner = owner;
        this.amount = amount;
        this.displayName = amount + "x Multiplier";
    }

    public MultiplierType getType() {
        return type;
    }

    public void setType(MultiplierType type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isNameVisible() {
        return nameVisible;
    }

    public void setNameVisible(boolean nameVisible) {
        this.nameVisible = nameVisible;
    }

}
