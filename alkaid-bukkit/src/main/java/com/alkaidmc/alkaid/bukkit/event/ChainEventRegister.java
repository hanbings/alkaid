package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.event.interfaces.AlkaidEventRegister;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ChainEventRegister implements AlkaidEventRegister {
    final JavaPlugin plugin;

    final List<SimpleEventRegister<? extends Event>> chain;
    final Map<Class<? extends Event>, Integer> index;
    final Map<UUID, Integer> schedules;
    final int schedule;
    final boolean multi;

    public <T extends Event> ChainEventRegister skewer(Class<T> event) {
        return skewer(event, t -> {
        }, EventPriority.NORMAL, false, chain.size());
    }

    public <T extends Event> ChainEventRegister skewer(Class<T> event, Consumer<T> listener) {
        return skewer(event, listener, EventPriority.NORMAL, false, chain.size());
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> ChainEventRegister skewer(Class<T> event, Consumer<T> listener,
                                                       EventPriority priority, boolean ignore,
                                                       int index) {
        chain.add(index, new SimpleEventRegister<>(plugin, event) {
                    @Override
                    public void register() {
                        plugin.getServer().getPluginManager().registerEvent(
                                event,
                                new Listener() {
                                },
                                priority,
                                (l, e) -> {
                                    // 判断该事件是否注销 / Check if the event is cancelled.
                                    if (cancel) {
                                        e.getHandlers().unregister(l);
                                        return;
                                    }
                                    listener.accept((T) e);
                                },
                                plugin,
                                ignore
                        );
                    }
                }.listener(listener).priority(priority).ignore(ignore)
        );
        return this;
    }

    @Override
    public void register() {
        chain.forEach(SimpleEventRegister::register);
    }

    @Override
    public void unregister() {
        chain.forEach(SimpleEventRegister::unregister);
    }
}
