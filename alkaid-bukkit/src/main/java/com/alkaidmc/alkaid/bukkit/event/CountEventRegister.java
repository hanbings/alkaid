package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.Alkaid;
import com.alkaidmc.alkaid.bukkit.interfaces.AlkaidEventCallback;
import com.alkaidmc.alkaid.bukkit.interfaces.AlkaidEventControllable;
import com.alkaidmc.alkaid.bukkit.interfaces.AlkaidEventCountable;
import com.alkaidmc.alkaid.bukkit.interfaces.AlkaidEventRegister;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public class CountEventRegister implements AlkaidEventRegister, AlkaidEventControllable, AlkaidEventCountable {
    Alkaid alkaid;
    // 需要监听的事件
    Class<? extends Event> event;
    // 事件处理器
    Consumer<Event> consumer;
    // 事件剩余次数
    int count = 0;
    // 事件是否挂起
    boolean hangup = false;
    // 开始监听前的调用
    AlkaidEventCallback before = null;
    // 停止监听后的调用
    AlkaidEventCallback after = null;
    // Bukkit 事件优先级
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志
    boolean ignore = false;
    // 注销事件
    boolean cancel = false;
    Listener listener = new Listener() {
        public void on(Event event) {
            // 检查事件是否已经被挂起
            if (hangup) {
                return;
            }
            // count 不为 0 不小于 0 即继续运行
            if (!(count > 0)) {
                after.callback(alkaid, CountEventRegister.this);
                return;
            }
            // 判断该事件是否注销
            if (cancel) {
                event.getHandlers().unregister(this);
                return;
            }
            consumer.accept(event);
            // 执行计数
            count--;
        }
    };

    public CountEventRegister(Alkaid alkaid) {
        this.alkaid = alkaid;
    }

    @Override
    public CountEventRegister count(int count) {
        this.count = count;
        return this;
    }

    @Override
    public CountEventRegister listener(Class<? extends Event> event) {
        this.event = event;
        return this;
    }

    @Override
    public CountEventRegister use(Consumer<Event> consumer) {
        this.consumer = consumer;
        return this;
    }

    @Override
    public CountEventRegister priority(EventPriority priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public CountEventRegister ignore(boolean ignore) {
        this.ignore = ignore;
        return this;
    }

    @Override
    public CountEventRegister register() {
        this.listen();
        alkaid.getServer().getPluginManager().registerEvent(
                event, listener, priority, (l, e) -> consumer.accept(e), alkaid, ignore
        );
        return this;
    }

    @Override
    public void unregister() {
        cancel = true;
    }

    @Override
    public void listen() {
        this.before.callback(alkaid, this);
        this.hangup = false;
    }

    @Override
    public void hangup() {
        this.after.callback(alkaid, this);
        this.hangup = true;
    }

    @Override
    public CountEventRegister before(AlkaidEventCallback callback) {
        this.before = callback;
        return this;
    }

    @Override
    public CountEventRegister after(AlkaidEventCallback callback) {
        this.after = callback;
        return this;
    }
}
