package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.Alkaid;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ListenerRegister {
    Alkaid alkaid;
    Listener voidListener = new Listener() {
    };

    public ListenerRegister(Alkaid alkaid) {
        this.alkaid = alkaid;
    }

    public ListenerRegister listener(Class<? extends Event> event, Consumer<Event> listener) {
        return listener(event, EventPriority.NORMAL, listener);
    }

    public ListenerRegister listener(Class<? extends Event> event, EventPriority priority, Consumer<Event> listener) {
        return listener(event, priority, false, listener);
    }

    public ListenerRegister listener(Class<? extends Event> event, EventPriority priority,
                                     boolean ignoreCancelled, Consumer<Event> listener) {
        alkaid.getServer().getPluginManager().registerEvent(
                event, voidListener, priority, (l, e) -> listener.accept(e), alkaid, ignoreCancelled
        );
        return this;
    }
}
