package com.lightcraftmc.guildwars.java.core.commands.types;

import java.util.HashMap;
import java.util.Random;

import mc.lightcraft.java.common.rank.tree.RankTree;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.lightcraftmc.guildwars.java.core.commands.Command;
import com.lightcraftmc.guildwars.java.core.main.GWCore;
import com.lightcraftmc.guildwars.java.core.util.LocalQueryUtil;

import depricated.com.lightcraftmc.guildwars.java.core.types.PDGuild;

public class CommandGuild extends Command {

    String[] defaults3 = new String[] { "The guild", "The one and only guild", "A guild" };

    String[] defaults = new String[] { " of only the world's greatest fighters", " of only the best", " of supremacy", " of great respect" };

    String[] defaults2 = new String[] { "", " for all", " for no-one", " for not a single soul", " for the greater good", "" };

    String[] defaults4 = new String[] { "!", "." };

    static HashMap<String, String> invited = new HashMap<String, String>();

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        String[] alts = new String[] { "guild", "g" };
        boolean cont = false;
        for (String s : alts) {
            if (label.equalsIgnoreCase(s)) {
                cont = true;
                break;
            }
        }

        if (!cont)
            return false;

        if (!(sender instanceof Player))
            return true;

        sender.sendMessage("§cThat command is currently disabled.");
        
   /*     if (args.length == 0) {
            sender.sendMessage("§bUsage > §7/guild create/invite/kick/disband");
            return true;
        }

        Player owner = (Player) sender;

        if (args[0].equalsIgnoreCase("join")) {
            if (args.length == 1) {
                sender.sendMessage("§bUsage > §7/guild join [guild]");
                return true;
            }

            if (!invited.containsKey(owner.getUniqueId().toString())) {
                sender.sendMessage("§cError > §7You have not been invited to a guild.");
                return true;
            }

            if (!invited.get(owner.getUniqueId().toString()).equalsIgnoreCase(args[1])) {
                sender.sendMessage("§cError > §7You have not been invited to a guild.");
                return true;
            }

            final String guildName = invited.get(owner.getUniqueId().toString());

            final String oName = owner.getName();
            final String oUUID = owner.getUniqueId().toString();

            Bukkit.getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), new Runnable() {
                public void run() {
                    Guild guild = null;

                    for (Guild g : Guild.getGuilds()) {
                        if (g.getName().equalsIgnoreCase(guildName)) {
                            guild = g;
                        }
                    }

                    if (guild == null) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                            public void run() {
                                sender.sendMessage("§cError > §7The guild you have been invited to does not exist.");
                                return;
                            }
                        }, 1);
                    }
                    guild.getMembers().put(oUUID, "default");
                    guild.saveToDB();
                    final Guild gg = guild;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                        public void run() {
                            for (String s : gg.getMembers().keySet()) {
                                for (Player p3 : Bukkit.getOnlinePlayers()) {
                                    if (p3.getUniqueId().toString().equals(s)) {
                                        final String[] send = new String[] { "§8§m--------------------------------------------", "§c§l" + oName + " joined your guild!",
                                                "§8§m--------------------------------------------"

                                        };
                                        p3.sendMessage(send);
                                    }
                                }
                            }
                        }
                    }, 1);

                }
            }, 1);

        }

        if (args[0].equalsIgnoreCase("invite")) {
            if (args.length == 1) {
                sender.sendMessage("§bUsage > §7/guild invite [player]");
                return true;
            }

            Player invitee2 = null;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().equalsIgnoreCase(args[1])) {
                    invitee2 = p;
                    break;
                }
            }

            if (invitee2 == null) {
                sender.sendMessage("§cError > §7" + args[1] + " is not a valid player.");
                sender.sendMessage("§cMake sure they are in the same world/server as you!");
                return true;
            }

            final Player invitee = invitee2;
            final String inviteeName = invitee2.getName();
            final String inviteeUniqueId = invitee2.getUniqueId().toString();
            Bukkit.getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), new Runnable() {
                public void run() {

                    Guild g = null;

                    for (Guild gg : Guild.getGuilds()) {
                        if (gg.getOwner().equals(owner.getUniqueId().toString())) {
                            g = gg;
                            break;
                        } else {
                            if (gg.getMembers().containsKey(owner.getUniqueId().toString()) && !gg.getOwner().equals(owner.getUniqueId().toString())) {
                                Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                                    public void run() {
                                        sender.sendMessage("§cError > §7You must be the guild's owner to invite people!");
                                    }
                                }, 1);
                                return;
                            }
                        }
                    }

                    if (g == null) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                            public void run() {
                                owner.sendMessage("§cError > §7You are not in a guild!");
                            }
                        }, 1);
                        return;
                    }
                    if (invited.containsKey(inviteeUniqueId) && invited.get(inviteeUniqueId).equals(g.getName())) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                            public void run() {
                                owner.sendMessage("§cError > §7You have already invited that player.");
                            }
                        }, 1);
                        return;
                    }

                    final String[] send = new String[] { "§8§m--------------------------------------------", "§7§lYou have been invited to join §b" + g.getName(),
                            "§9To join: §7/guild join " + g.getName(), "§8§m--------------------------------------------"

                    };
                    invited.put(inviteeUniqueId, g.getName());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                        public void run() {
                            invitee.playSound(owner.getLocation(), Sound.LEVEL_UP, 2f, 2f);
                            invitee.sendMessage(send);
                            sender.sendMessage("§aSuccess > §7Player has been invited!");
                        }
                    }, 1);

                }
            }, 1);
            RankTree.getTree().unloadRank(owner);
            LocalQueryUtil.handleBan(owner);
            LocalQueryUtil.handleRank(owner, false);
            return true;

        }

        if (args[0].equalsIgnoreCase("create")) {

            if (args.length == 1) {
                sender.sendMessage("§bUsage > §7/guild create [name]");
                return true;
            }

            Bukkit.getScheduler().scheduleAsyncDelayedTask(GWCore.getInstance(), new Runnable() {
                public void run() {
                    for (Guild g2 : Guild.getGuilds()) {
                        try {
                            if (g2.getName().equalsIgnoreCase(args[1])) {
                                Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                                    public void run() {
                                        owner.playSound(owner.getLocation(), Sound.NOTE_BASS, 0.5f, 0.5f);
                                        owner.sendMessage("§cError > §7Sorry, but a guild with that name already exists.");
                                    }
                                }, 1);
                                return;
                            }
                        } catch (Exception ex) {

                        }
                    }
                    String defaultDescription = defaults3[new Random().nextInt(defaults3.length - 1)] + defaults[new Random().nextInt(defaults.length - 1)]
                            + defaults2[new Random().nextInt(defaults2.length - 1)] + defaults4[new Random().nextInt(defaults4.length - 1)];
                    for (Guild g2 : Guild.getGuilds()) {
                        if (g2.getOwner().equals(owner.getUniqueId().toString())) {
                            Guild.disbandGuild(g2);
                        }
                    }

                    final Guild g = new Guild(args[1], defaultDescription, owner.getUniqueId().toString());
                    g.saveToDB();

                    Bukkit.getScheduler().scheduleSyncDelayedTask(GWCore.getInstance(), new Runnable() {
                        public void run() {
                            owner.playSound(owner.getLocation(), Sound.LEVEL_UP, 2f, 2f);
                            owner.sendMessage("§8§m--------------------------------------------");
                            sender.sendMessage("§a§lGuild Created!");
                            sender.sendMessage("§7Name: " + g.getName());
                            sender.sendMessage("§7Description: " + g.getDescription());
                            sender.sendMessage("§e§lSearch for players with /recruit!");
                            owner.sendMessage("§8§m--------------------------------------------");
                        }
                    }, 1);
                    RankTree.getTree().unloadRank(owner);
                    LocalQueryUtil.handleBan(owner);
                    LocalQueryUtil.handleRank(owner, false);
                }
            }, 1);

            return true;

        }
*/
        return true;
    }
}
