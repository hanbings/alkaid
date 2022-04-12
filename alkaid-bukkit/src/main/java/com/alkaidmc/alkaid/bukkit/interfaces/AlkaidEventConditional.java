package com.alkaidmc.alkaid.bukkit.interfaces;

import org.bukkit.event.Event;

public interface AlkaidEventConditional {
    AlkaidEventConditional interrupt(Class<? extends Event> event);
}
