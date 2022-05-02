package com.alkaidmc.alkaid.bukkit.event;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AlkaidEvent {
    final JavaPlugin plugin;

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
