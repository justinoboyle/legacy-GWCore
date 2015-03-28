package com.lightcraftmc.guildwars.java.core.main;

import mc.lightcraft.java.remote.LCDatabase.DatabaseManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandAdminKit;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandFlySpeed;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandGamemode;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandGuild;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandLoadStands;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandRank;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandReport;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandSaveStands;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandSendPack;
import com.lightcraftmc.guildwars.java.core.commands.types.CommandTP;
import com.lightcraftmc.guildwars.java.core.currency.Currencies;
import com.lightcraftmc.guildwars.java.core.currency.Currency;
import com.lightcraftmc.guildwars.java.core.listener.chat.ChatListener;
import com.lightcraftmc.guildwars.java.core.listener.chat.ConnectionListener;
import com.lightcraftmc.guildwars.java.core.listener.world.BuildSettings;
import com.lightcraftmc.guildwars.java.core.listener.world.ClickArmorstandEvent;
import com.lightcraftmc.guildwars.java.core.listener.world.ClickEntityEvent;
import com.lightcraftmc.guildwars.java.core.listener.world.ExplosionListener;
import com.lightcraftmc.guildwars.java.core.listener.world.LaunchPadEvent;
import com.lightcraftmc.guildwars.java.core.npc.NPCInteractListener;
import com.lightcraftmc.guildwars.java.core.npc.NPCList;
import com.lightcraftmc.guildwars.java.core.util.LocalQueryUtil;
import com.lightcraftmc.guildwars.java.util.classes.UtilClass;

public class GWCore extends JavaPlugin implements PluginMessageListener {

    private static GWCore instance;
    private DatabaseManager dbManager;

    public DatabaseManager getDBManager() {
        return dbManager;
    }

    public static GWCore getInstance() {
        return instance;
    }

    @SuppressWarnings("deprecation")
    public void onEnable() {
        instance = this;
        dbManager = new DatabaseManager();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        getConfig();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                GWScoreboard.setupScoreboards();
            }
        }, 0, 20 * 5);

        reloadConfig();
        if (getConfig().get("server") == null || getConfig().getString("server").equals("")) {
            getConfig().set("server", "General");
        }
        if (getConfig().get("spawnworld") == null || getConfig().getString("spawnworld").equals("")) {
            getConfig().set("spawnworld", "GuildWars_HUB02");
        }
        dbManager.setAccessToken(getConfig().getString("database.accesstoken"));
        dbManager.setHost(getConfig().getString("database.host"));
        if (dbManager.getHost() == null) {
            dbManager.setHost("localhost:412");
        }
        saveConfig();
        setupEvents();

        for (Player p : Bukkit.getOnlinePlayers()) {
            LocalQueryUtil.handleBan(p);
            LocalQueryUtil.handleRank(p, false);
        }
        
        UtilClass.setupRunnable();
        NPCList.setup();
        Currency.init();
        Currencies.init();
        GWScoreboard.setupScoreboards();
    }

    private void setupEvents() {
        Listener[] listeners = new Listener[] { new ChatListener(), new ConnectionListener(), new BuildSettings(), new ExplosionListener(), new ClickArmorstandEvent(), new LaunchPadEvent(),
                new ClickEntityEvent(), new NPCInteractListener() };

        for (Listener l : listeners)
            Bukkit.getServer().getPluginManager().registerEvents(l, this);

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        com.lightcraftmc.guildwars.java.core.commands.Command[] commands = new com.lightcraftmc.guildwars.java.core.commands.Command[] {

        new CommandGamemode(), new CommandRank(), new CommandTP(), new CommandReport(), new CommandGuild(), new CommandSendPack(), new CommandFlySpeed(), new CommandAdminKit(),
                new CommandSaveStands(), new CommandLoadStands()

        };

        for (com.lightcraftmc.guildwars.java.core.commands.Command cmd : commands) {
            if (cmd.onCommand(sender, label, args))
                return true;
        }

        return false;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("SomeSubChannel")) {
            // Use the code sample in the 'Response' sections below to read
            // the data.
        }
    }

}
