package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.Alkaid;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AlkaidEvent {
    Alkaid alkaid;
    Listener voidListener = new Listener() {
    };

    public AlkaidEvent(Alkaid alkaid) {
        this.alkaid = alkaid;
    }

    public AlkaidEvent listener(Class<? extends Event> event, Consumer<Event> listener) {
        return listener(event, EventPriority.NORMAL, listener);
    }

    public AlkaidEvent listener(Class<? extends Event> event, EventPriority priority, Consumer<Event> listener) {
        return listener(event, priority, false, listener);
    }

    public AlkaidEvent listener(Class<? extends Event> event, EventPriority priority,
                                boolean ignoreCancelled, Consumer<Event> listener) {
        alkaid.getServer().getPluginManager().registerEvent(
                event, voidListener, priority, (l, e) -> listener.accept(e), alkaid, ignoreCancelled
        );
        return this;
    }
}
