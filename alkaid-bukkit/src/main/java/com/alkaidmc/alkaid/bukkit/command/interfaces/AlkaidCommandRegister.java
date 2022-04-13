package com.alkaidmc.alkaid.bukkit.command.interfaces;

public interface AlkaidCommandRegister {
    AlkaidCommandRegister command(String command);

    AlkaidCommandRegister register();

    void unregister();
}
