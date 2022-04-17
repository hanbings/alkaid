package com.alkaidmc.alkaid.bukkit.event.interfaces;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public interface AlkaidEventCallback {
    void callback(JavaPlugin plugin, AlkaidEventRegister register);
}
