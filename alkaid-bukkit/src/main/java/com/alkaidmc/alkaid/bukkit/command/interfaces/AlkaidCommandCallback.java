package com.alkaidmc.alkaid.bukkit.command.interfaces;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface AlkaidCommandCallback {
    boolean execute(CommandSender sender, Command command, String aliases, String[] args);
}
