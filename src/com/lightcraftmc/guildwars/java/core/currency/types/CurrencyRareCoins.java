package com.lightcraftmc.guildwars.java.core.currency.types;

import com.lightcraftmc.guildwars.java.core.currency.Currency;

public class CurrencyRareCoins extends Currency {

    @Override
    public String getDisplayName() {
        return "Rare Coins";
    }

    @Override
    public String getFormatting() {
        return "§d";
    }

    @Override
    public String getSaveName() {
        return "rarecoins";
    }

}
