package com.alkaidmc.alkaid.bukkit.event;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AlkaidEvent {
    final JavaPlugin plugin;

    public <T extends Event> SimpleEventRegister<T> simple(Class<T> event) {
        return new SimpleEventRegister<>(plugin, event);
    }

    public <T extends Event> CountEventRegister<T> count(Class<T> event) {
        return new CountEventRegister<>(plugin, event);
    }

    public <T extends Event> ConditionalEventRegister<T> conditional(Class<T> event) {
        return new ConditionalEventRegister<>(plugin, event);
    }
}
