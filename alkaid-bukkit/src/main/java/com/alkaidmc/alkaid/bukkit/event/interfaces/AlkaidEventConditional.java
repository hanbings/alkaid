package com.alkaidmc.alkaid.bukkit.event.interfaces;

import org.bukkit.event.Event;

@SuppressWarnings("unused")
public interface AlkaidEventConditional {
    AlkaidEventConditional interrupt(Class<? extends Event> event);
}
