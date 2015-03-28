package com.lightcraftmc.guildwars.java.core.commands;

import org.bukkit.command.CommandSender;

public abstract class Command {

    public abstract boolean onCommand(CommandSender sender, String label, String[] args);
    
}
