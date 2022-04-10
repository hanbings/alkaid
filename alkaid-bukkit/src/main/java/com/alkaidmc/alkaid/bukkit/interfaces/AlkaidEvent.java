package com.alkaidmc.alkaid.bukkit.interfaces;

import org.bukkit.event.Event;

import java.util.function.Consumer;

public interface AlkaidEvent {
    AlkaidEvent listener(Event event, Consumer<Event> listener);
}
