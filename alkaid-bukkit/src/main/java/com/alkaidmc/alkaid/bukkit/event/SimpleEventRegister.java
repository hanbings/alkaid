package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.Alkaid;
import com.alkaidmc.alkaid.bukkit.event.interfaces.AlkaidEventRegister;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class SimpleEventRegister implements AlkaidEventRegister {
    Alkaid alkaid;
    // 需要监听的事件
    Class<? extends Event> event;
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

    public SimpleEventRegister(Alkaid alkaid) {
        this.alkaid = alkaid;
    }

    @Override
    public SimpleEventRegister listener(Class<? extends Event> event) {
        this.event = event;
        return this;
    }

    @Override
    public SimpleEventRegister use(Consumer<Event> consumer) {
        this.consumer = consumer;
        return this;
    }

    @Override
    public SimpleEventRegister priority(EventPriority priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public SimpleEventRegister ignore(boolean ignore) {
        this.ignore = ignore;
        return this;
    }

    @Override
    public SimpleEventRegister register() {
        alkaid.getServer().getPluginManager().registerEvent(
                event, listener, priority, (l, e) -> consumer.accept(e), alkaid, ignore
        );
        return this;
    }

    @Override
    public void unregister() {
        cancel = true;
    }
}
