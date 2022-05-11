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
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.IntStream;


@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ChainEventRegister implements AlkaidEventRegister {
    final JavaPlugin plugin;

    // Bukkit 事件优先级 / Bukkit event priority.
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志 / Whether to ignore Bukkit event cancellation flag.
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    boolean ignore = false;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    boolean multi = false;

    List<SkewerEventRegister<? extends Event>> chain = new ArrayList<>();
    Map<Class<? extends Event>, Integer> index = new HashMap<>();
    Map<UUID, Integer> schedules;
    int schedule;

    public <T extends Event> ChainEventRegister skewer(Class<T> event) {
        return skewer(event,
                l -> true,
                this.priority,
                this.ignore,
                this.chain.size());
    }

    public <T extends Event> ChainEventRegister skewer(Class<T> event, Predicate<T> listener) {
        return skewer(event,
                listener,
                this.priority,
                this.ignore,
                this.chain.size());
    }

    public <T extends Event> ChainEventRegister skewer(Class<T> event, Predicate<T> listener,
                                                       EventPriority priority, boolean ignore,
                                                       int index) {
        this.chain.add(index,
                new SkewerEventRegister<>(plugin, event, this)
                        .listener(listener)
                        .priority(priority)
                        .ignore(ignore)
        );
        return this;
    }

    @Override
    public void register() {
        // 创建索引 / Create the index.
        IntStream.range(0, chain.size())
                .forEach(count -> index.put(chain.get(count).event(), count));

        // 注册事件 / Register events.
        chain.forEach(SkewerEventRegister::register);
    }

    @Override
    public void unregister() {
        chain.forEach(SkewerEventRegister::unregister);
    }

    @Setter
    @Getter
    @RequiredArgsConstructor
    @SuppressWarnings("unused")
    @Accessors(fluent = true, chain = true)
    public static class SkewerEventRegister<T extends Event> implements AlkaidEventRegister {
        final JavaPlugin plugin;
        // 需要监听的事件 / Event to listen.
        final Class<T> event;
        // 事件链引用 / Event chain reference.
        final ChainEventRegister chain;
        // 空监听器
        final static Listener NULL_LISTENER = new Listener() {
        };

        // 事件处理器 / Event handler.
        Predicate<T> listener;
        // Bukkit 事件优先级 / Bukkit event priority.
        EventPriority priority = EventPriority.NORMAL;
        // 是否忽略  Bukkit 事件的取消标志 / Whether to ignore Bukkit event cancellation flag.
        boolean ignore = false;

        // 注销事件标志 / Unregister event flag.
        @Setter(AccessLevel.NONE)
        @Getter(AccessLevel.NONE)
        boolean cancel = false;

        @Override
        @SuppressWarnings("unchecked")
        public void register() {
            EventExecutor executor;

            if (chain.multi()) {
                if (chain.schedules == null) {
                    chain.schedules = new HashMap<>();
                }

                executor = (l, e) -> {
                    // 判断该事件是否注销 / Check if the event is cancelled.
                    // 判断事件是否在索引中 / Check if the event is in the index.
                    if (cancel ||
                            !chain.index.containsKey(e.getClass()) ||
                            !(e instanceof PlayerEvent)
                    ) {
                        e.getHandlers().unregister(l);
                        return;
                    }

                    // 获取索引 / Get the index.
                    int index = chain.index.get(e.getClass());
                    // 获取进度 / Get the progress.
                    int progress = chain.schedules.getOrDefault(
                            ((PlayerEvent) e).getPlayer().getUniqueId(),
                            0
                    );

                    // 判断进度是否达到预期 / Check if the progress is reached.
                    if (progress != 0 && progress != index - 1) {
                        return;
                    }

                    // 检查是否需要复位进度 / Check if the progress needs to be reset.
                    // 设置进度 / Set the progress.
                    chain.schedules.put(
                            ((PlayerEvent) e).getPlayer().getUniqueId(),
                            listener.test((T) e) ? progress : 0
                    );
                };
            } else {
                executor = (l, e) -> {
                    if (cancel ||
                            !chain.index.containsKey(e.getClass())
                    ) {
                        e.getHandlers().unregister(l);
                        return;
                    }

                    // 获取索引 / Get the index.
                    int index = chain.index.get(e.getClass());

                    // 判断进度是否达到预期 / Check if the progress is reached.
                    if (chain.schedule != 0 && chain.schedule != index - 1) {
                        return;
                    }

                    // 设置进度 / Set the progress.
                    chain.schedule = index;

                    // 检查是否需要复位进度 / Check if the progress needs to be reset.
                    chain.schedule = listener.test((T) e) ? index : 0;
                };
            }

            plugin.getServer().getPluginManager().registerEvent(
                    event,
                    NULL_LISTENER,
                    priority,
                    executor,
                    plugin,
                    ignore
            );
        }

        @Override
        public void unregister() {
            cancel = true;
        }
    }
}
