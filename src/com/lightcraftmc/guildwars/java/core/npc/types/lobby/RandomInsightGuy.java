package com.lightcraftmc.guildwars.java.core.npc.types.lobby;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.lightcraftmc.guildwars.java.core.currency.Currencies;
import com.lightcraftmc.guildwars.java.core.npc.NPC;
import com.lightcraftmc.guildwars.java.core.util.BUtils;
import com.lightcraftmc.guildwars.java.core.util.multiplier.UtilMultiplier;
import com.lightcraftmc.guildwars.java.util.schedule.UtilSchedule;

public class RandomInsightGuy extends NPC {

    @Override
    public String getExactName() {
        return "Random Insight Guy";
    }

    String[] welcome = new String[] { "Some say I'm crazy, the others find the land of Guild Wars' most hidden secrets.", "Sometimes, art is utilized to hide past failures.",
            "Sometimes, art is utilized to hide past failures.", "Sometimes, art is utilized to hide past failures.", "A door is two meters tall, and so are you.",
            "Sometimes, those of highest social class do not even realize the power they have access to." };

    String[] find = new String[] { "Bet you didn't expect me here. In fact, nobody does. Don't worry about how I got here.",
            "%givereward%Anyway, I applaud you for taking my vague advice. Here's a little reward.",
            "%goaway%Now, I'm sending you back up! You heard nothing. You saw nothing. You don't even know that I exist. Do I?" };

    @Override
    public void interact(Player p, LivingEntity e) {
        if (p.getLocation().getY() > 30) {
            this.talk(p, welcome[new Random().nextInt(welcome.length - 1)].replace("%name%", p.getName()));
            p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 2, 0);
            return;
        }
        int i = 0;
        for (String s : find) {
            UtilSchedule.scheduleSync(new Runnable() {
                public void run() {
                    runString(s, p);
                }
            }, ((20 * 4) * i));
            i++;
        }

    }

    @Override
    public boolean isCurrentlyInBeta() {
        return false;
    }

    @SuppressWarnings("deprecation")
    public void runString(String s, Player p) {
        p.sendMessage("");
        this.talk(p, s.replace("%goaway%", "").replace("%givereward%", ""));
        p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 2, 0);
        if (s.contains("%givereward%")) {
            Currencies.RARE_COINS.give(50, p);
            BUtils.sendActionBar(p, UtilMultiplier.generateRareCoinsString(50, "§kSecrets§7"));
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 2, 2);
        }
        if (s.contains("%goaway%")) {
            p.teleport(new Location(p.getWorld(), 6, 80, 44, 135, 0));
            p.playEffect(p.getLocation(), Effect.PORTAL, 1);
            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
        }

    }

}
