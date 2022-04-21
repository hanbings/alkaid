package com.alkaidmc.alkaid.bukkit.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class SimpleEventRegister {
    final JavaPlugin plugin;
    // 需要监听的事件
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Class<? extends Event> event;
    // 事件处理器
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<Event> consumer;
    // Bukkit 事件优先级
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    boolean ignore = false;

    // 注销事件标志
    boolean cancel = false;

    public void register() {
        plugin.getServer().getPluginManager().registerEvent(
                event,
                new Listener() {
                },
                priority,
                (l, e) -> {
                    // 判断该事件是否注销
                    if (cancel) {
                        e.getHandlers().unregister(l);
                        return;
                    }
                    consumer.accept(e);
                },
                plugin,
                ignore
        );
    }

    public void unregister() {
        cancel = true;
    }
}
