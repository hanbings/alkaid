package com.alkaidmc.alkaid.bukkit.extra.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class SecondEvent extends Event {
    static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public long getSecond() {
        return System.currentTimeMillis();
    }
}
