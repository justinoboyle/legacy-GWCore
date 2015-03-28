package com.lightcraftmc.guildwars.java.core.npc;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NPCInteractListener implements Listener {

    @EventHandler
    public void interact(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (!(e.getRightClicked() instanceof LivingEntity))
            return;
        LivingEntity en = (LivingEntity) e.getRightClicked();
        if (en.getCustomName() == null)
            return;
        String s = en.getCustomName();
        for (NPC n : NPCList.getNPCList()) {
            if (ChatColor.stripColor(s.toLowerCase()).equalsIgnoreCase(ChatColor.stripColor(n.getExactName().toLowerCase()))) {
                if (p.isOp() && p.getGameMode().equals(GameMode.CREATIVE)) {
                    if (!p.getItemInHand().getType().equals(Material.AIR) && !(p.getItemInHand().getType() == null) && !(p.getItemInHand().toString().contains("AIR"))) {
                        p.sendMessage("§7§oTo interact with NPCs in Creative mode, right-click on them with an empty hand.");
                        return;
                    }

                }
                e.setCancelled(true);
                n.interact(p, en);
            }

        }
    }

}
