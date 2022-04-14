package com.alkaidmc.alkaid.bukkit.command.interfaces;

import org.bukkit.command.CommandSender;

import java.util.function.Consumer;

public interface AlkaidCommandMatchable {
    AlkaidCommandMatchable match(Consumer<CommandSender> callback);

    AlkaidCommandMatchable match(String regular, AlkaidCommandCallback callback);

    AlkaidCommandMatchable tab(String regular, AlkaidTabCallback callback);
}
