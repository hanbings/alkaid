package com.alkaidmc.alkaid.bukkit.command.interfaces;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public interface AlkaidCommandExecutable {

    AlkaidCommandExecutable execute(CommandExecutor executor);

    AlkaidCommandExecutable tab(TabCompleter completer);
}
