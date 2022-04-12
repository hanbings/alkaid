package com.alkaidmc.alkaid.bukkit.interfaces;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.function.Consumer;

public interface AlkaidEventRegister {
    AlkaidEventRegister listener(Class<? extends Event> event);

    AlkaidEventRegister use(Consumer<Event> consumer);

    AlkaidEventRegister priority(EventPriority priority);

    AlkaidEventRegister ignore(boolean ignore);

    AlkaidEventRegister register();

    void unregister();
}
