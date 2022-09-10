package com.alkaidmc.alkaid.bukkit.extra.event;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class TickEvent extends Event {
    static final HandlerList HANDLERS_LIST = new HandlerList();
    final long tick;

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public long getTick() {
        return this.tick;
    }
}
