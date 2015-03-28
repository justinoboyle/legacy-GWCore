package com.lightcraftmc.guildwars.java.core.currency;

import com.lightcraftmc.guildwars.java.core.currency.types.CurrencyCoins;
import com.lightcraftmc.guildwars.java.core.currency.types.CurrencyExperience;
import com.lightcraftmc.guildwars.java.core.currency.types.CurrencyRareCoins;

public class Currencies {

    public static Currency COINS;
    public static Currency EXPERIENCE;
    public static Currency RARE_COINS;
    
    public static void init(){
        COINS = new CurrencyCoins();
        EXPERIENCE = new CurrencyExperience();
        RARE_COINS = new CurrencyRareCoins();
    }
    

}
