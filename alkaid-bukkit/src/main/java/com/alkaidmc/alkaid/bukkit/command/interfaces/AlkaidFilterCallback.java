package com.alkaidmc.alkaid.bukkit.command.interfaces;

import org.bukkit.command.CommandSender;

public interface AlkaidFilterCallback {
    boolean filter(CommandSender sender, String command, String label, String[] args);
}
