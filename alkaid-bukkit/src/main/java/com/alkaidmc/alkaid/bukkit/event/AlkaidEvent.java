package com.alkaidmc.alkaid.bukkit.event;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class AlkaidEvent {
    JavaPlugin plugin;

    public AlkaidEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public SimpleEventRegister simple() {
        return new SimpleEventRegister(plugin);
    }

    public CountEventRegister count() {
        return new CountEventRegister(plugin);
    }

    public ConditionalEventRegister conditional() {
        return new ConditionalEventRegister(plugin);
    }

}
