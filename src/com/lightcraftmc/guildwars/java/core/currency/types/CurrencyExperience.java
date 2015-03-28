package com.lightcraftmc.guildwars.java.core.currency.types;

import com.lightcraftmc.guildwars.java.core.currency.Currency;

public class CurrencyExperience extends Currency {

    @Override
    public String getDisplayName() {
        return "Experience";
    }

    @Override
    public String getFormatting() {
        return "§a§l";
    }

    @Override
    public String getSaveName() {
        return "exp";
    }

}
