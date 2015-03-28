package com.lightcraftmc.guildwars.java.core.currency.types;

import com.lightcraftmc.guildwars.java.core.currency.Currency;

public class CurrencyCoins extends Currency {

    @Override
    public String getDisplayName() {
        return "Coins";
    }

    @Override
    public String getFormatting() {
        return "§7§l";
    }

    @Override
    public String getSaveName() {
        return "coins";
    }

}
