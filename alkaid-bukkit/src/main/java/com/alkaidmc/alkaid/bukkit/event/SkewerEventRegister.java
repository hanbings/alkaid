package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.event.interfaces.AlkaidEventRegister;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class SkewerEventRegister<T extends Event> implements AlkaidEventRegister {
    final JavaPlugin plugin;

    // 上下文信息 / context information
    final List<SimpleEventRegister<? extends Event>> chain;
    final Map<Class<? extends Event>, Integer> index;
    final Map<UUID, Integer> schedules;
    final int schedule;
    final boolean multi;

    final Class<T> event;

    Consumer<T> listener;
    EventPriority priority = EventPriority.NORMAL;
    boolean ignore = false;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean cancel = false;

    @Override
    @SuppressWarnings("unchecked")
    public void register() {
        plugin.getServer().getPluginManager().registerEvent(
                event,
                new Listener() {
                },
                priority,
                (l, e) -> {
                    // 判断该事件是否注销 / Check if the event is cancelled.
                    if (cancel || !index.containsKey(e.getClass())) {
                        e.getHandlers().unregister(l);
                        return;
                    }
                    int dex = index.get(e.getClass());
                    listener.accept((T) e);
                },
                plugin,
                ignore
        );
    }

    @Override
    public void unregister() {
        cancel = true;
    }
}
