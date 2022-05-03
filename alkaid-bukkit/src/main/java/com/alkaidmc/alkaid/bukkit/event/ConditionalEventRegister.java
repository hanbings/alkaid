package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.event.interfaces.AlkaidEventRegister;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ConditionalEventRegister<T extends Event> implements AlkaidEventRegister {
    final JavaPlugin plugin;

    // 需要监听的事件
    @Getter
    @Accessors(fluent = true)
    Class<T> event;
    // 结束条件的事件
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Class<? extends Event> interrupt;
    // 事件处理器
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<T> listener;
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

    // 注销事件
    boolean cancel = false;

    public ConditionalEventRegister(JavaPlugin plugin, Class<T> event) {
        this.plugin = plugin;
        this.event = event;
    }

    @Override
    @SuppressWarnings("unchecked")
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
                    listener.accept((T) e);
                },
                plugin,
                ignore
        );
        plugin.getServer().getPluginManager().registerEvent(
                interrupt, new Listener() {
                }, priority, (l, e) -> cancel = true, plugin, ignore
        );
    }

    @Override
    public void unregister() {
        cancel = true;
    }
}
