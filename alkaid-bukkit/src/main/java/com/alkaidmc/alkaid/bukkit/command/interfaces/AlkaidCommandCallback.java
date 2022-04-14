package com.alkaidmc.alkaid.bukkit.command.interfaces;

import org.bukkit.command.CommandSender;

public interface AlkaidCommandCallback {
    boolean execute(CommandSender sender, String[] args);
}
