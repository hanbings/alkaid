package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.event.interfaces.AlkaidEventConditional;
import com.alkaidmc.alkaid.bukkit.event.interfaces.AlkaidEventRegister;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ConditionalEventRegister implements AlkaidEventRegister, AlkaidEventConditional {
    JavaPlugin plugin;
    // 需要监听的事件
    Class<? extends Event> event;
    // 结束条件的事件
    Class<? extends Event> interrupt;
    // 事件处理器
    Consumer<Event> consumer;
    // Bukkit 事件优先级
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志
    boolean ignore = false;
    // 注销事件
    boolean cancel = false;
    // 监听器
    Listener listener = new Listener() {
        public void on(Event event) {
            // 判断该事件是否注销
            if (cancel) {
                event.getHandlers().unregister(this);
                return;
            }
            consumer.accept(event);
        }
    };

    public ConditionalEventRegister(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public ConditionalEventRegister listener(Class<? extends Event> event) {
        this.event = event;
        return this;
    }

    @Override
    public ConditionalEventRegister use(Consumer<Event> consumer) {
        this.consumer = consumer;
        return this;
    }

    @Override
    public ConditionalEventRegister priority(EventPriority priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public ConditionalEventRegister ignore(boolean ignore) {
        this.ignore = ignore;
        return this;
    }

    @Override
    public ConditionalEventRegister register() {
        plugin.getServer().getPluginManager().registerEvent(
                event, listener, priority, (l, e) -> consumer.accept(e), plugin, ignore
        );
        plugin.getServer().getPluginManager().registerEvent(
                interrupt, listener, priority, (l, e) -> cancel = true, plugin, ignore
        );
        return this;
    }

    @Override
    public void unregister() {
        cancel = true;
    }

    @Override
    public ConditionalEventRegister interrupt(Class<? extends Event> event) {
        this.interrupt = event;
        return this;
    }
}
