package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.event.interfaces.AlkaidEventCallback;
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

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class CountEventRegister implements AlkaidEventRegister {
    final JavaPlugin plugin;
    // 需要监听的事件
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Class<? extends Event> with;
    // 事件处理器
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<Event> use;
    // 事件剩余次数
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    int count = 0;
    // 开始监听前的调用
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    AlkaidEventCallback before = null;
    // 停止监听后的调用
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    AlkaidEventCallback after = null;
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

    // 事件是否挂起
    boolean hangup = false;
    // 注销事件
    boolean cancel = false;


    public void listen() {
        this.before.callback(plugin, this);
        this.hangup = false;
    }

    public void hangup() {
        this.after.callback(plugin, this);
        this.hangup = true;
    }

    @Override
    public void register() {
        this.listen();
        plugin.getServer().getPluginManager().registerEvent(
                with,
                new Listener() {
                },
                priority,
                (l, e) -> {
                    // 检查事件是否已经被挂起
                    if (hangup) {
                        return;
                    }
                    // count 不为 0 不小于 0 即继续运行
                    if (!(count > 0)) {
                        after.callback(plugin, CountEventRegister.this);
                        return;
                    }
                    // 判断该事件是否注销
                    if (cancel) {
                        e.getHandlers().unregister(l);
                        return;
                    }
                    use.accept(e);
                    // 执行计数
                    count--;

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
