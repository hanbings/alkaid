package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.event.interfaces.AlkaidEventRegister;
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
import java.util.function.Consumer;
import java.util.stream.IntStream;


@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ChainEventRegister implements AlkaidEventRegister {
    final JavaPlugin plugin;

    // 事件处理器 / Event handler.
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<? extends Event> listener = event -> {
    };
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

    List<SimpleEventRegister<? extends Event>> chain = new ArrayList<>();
    Map<Class<? extends Event>, Integer> index = new HashMap<>();
    Map<UUID, Integer> schedules;
    int schedule;

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
                    final ChainEventRegister chain = ChainEventRegister.this;

                    @Override
                    public void register() {
                        EventExecutor executor;

                        if (multi) {
                            if (schedules == null) {
                                schedules = new HashMap<>();
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
                                int progress = schedules.getOrDefault(
                                        ((PlayerEvent) e).getPlayer().getUniqueId(),
                                        0
                                );

                                // 判断进度是否达到预期 / Check if the progress is reached.
                                if (progress != 0 && progress != index - 1) {
                                    return;
                                }

                                // 设置进度 / Set the progress.
                                schedules.put(
                                        ((PlayerEvent) e).getPlayer().getUniqueId(),
                                        progress
                                );

                                listener.accept((T) e);
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
                                if (schedule != 0 && schedule != index - 1) {
                                    return;
                                }

                                // 设置进度 / Set the progress.
                                schedule = index;

                                listener.accept((T) e);
                            };
                        }

                        plugin.getServer().getPluginManager().registerEvent(
                                event,
                                new Listener() {
                                },
                                priority,
                                executor,
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
        // 创建索引 / Create the index.
        IntStream.range(0, chain.size())
                .forEach(count -> index.put(chain.get(count).event(), count));

        // 注册事件 / Register events.
        chain.forEach(SimpleEventRegister::register);
    }

    @Override
    public void unregister() {
        chain.forEach(SimpleEventRegister::unregister);
    }
}
