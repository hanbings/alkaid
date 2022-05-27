/*
 * Copyright 2022 Alkaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * <p> zh </p>
 * 这个注册器是事件链注册器 <br>
 * 它可以将多个事件串起来 并且从第二个事件开始以前一个条件为监听条件 <br>
 * 即第一个事件被触发后第二个事件才会被触发 依次类推 <br>
 * 事件链条不限制长度 允许相同的事件添加到链 <br>
 * 使用 {@link #depend(Class)} 添加事件到链条 <br>
 * 也可以使用 {@link #depend(Class, Predicate)} 添加事件到链条 Predicate 参数为事件监听器
 * 返回 true 表示继续监听链条的下一个事件 返回 false 表示复位当前链条从头开始监听 <br>
 * 使用 {@link #unregister()} 从 bukkit 中注销当前链条 <br>
 * <p> en </p>
 * This register is event chain register <br>
 * It can chain multiple events and the second event will be listened by the first event's condition <br>
 * Such as the first event will be triggered and the second event will be triggered after the first event <br>
 * Event chain is not limited to the length and can add the same event to the chain <br>
 * Use {@link #depend(Class)} to add event to the chain <br>
 * Also can use {@link #depend(Class, Predicate)} to add event to the chain.
 * Predicate parameter is the event listener.
 * Return true means continue listening the next event.
 * Return false means reset the chain to listen the first event again. <br>
 * Use {@link #unregister()} to unregister the chain from bukkit.
 */
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ChainEventRegister implements AlkaidEventRegister {
    final Plugin plugin;

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
    boolean entity = false;

    List<SkewerEventRegister<? extends Event>> chain = new ArrayList<>();
    Map<Class<? extends Event>, Set<Integer>> indexes = new HashMap<>();
    Map<UUID, Integer> schedules;
    int schedule;

    public <T extends Event> ChainEventRegister depend(Class<T> event) {
        return depend(event,
                l -> true,
                this.priority,
                this.ignore,
                this.chain.size(),
                null
        );
    }

    public <T extends Event> ChainEventRegister depend(Class<T> event, Consumer<List<Predicate<T>>> filters) {
        return depend(event,
                l -> true,
                this.priority,
                this.ignore,
                this.chain.size(),
                filters
        );
    }

    public <T extends Event> ChainEventRegister depend(Class<T> event, Predicate<T> listener) {
        return depend(event,
                listener,
                this.priority,
                this.ignore,
                this.chain.size(),
                null
        );
    }

    public <T extends Event> ChainEventRegister depend(Class<T> event,
                                                       Predicate<T> listener,
                                                       Consumer<List<Predicate<T>>> filters) {
        return depend(event,
                listener,
                this.priority,
                this.ignore,
                this.chain.size(),
                filters
        );
    }

    public <T extends Event> ChainEventRegister depend(Class<T> event, Predicate<T> listener,
                                                       EventPriority priority, boolean ignore,
                                                       int index,
                                                       Consumer<List<Predicate<T>>> filters) {
        if (filters == null) {
            this.chain.add(index, new SkewerEventRegister<>(plugin, event, this)
                    .listener(listener)
                    .priority(priority)
                    .ignore(ignore)
            );
            return this;
        }

        filters.andThen(f -> this.chain.add(index, new SkewerEventRegister<>(plugin, event, this)
                .listener(listener)
                .priority(priority)
                .ignore(ignore)
                .filters(f)
        )).accept(new ArrayList<>());
        return this;
    }

    @Override
    public void register() {
        // 创建索引 / Create the index.
        IntStream.range(0, chain.size())
                .forEach(count -> {
                    Class<? extends Event> event = chain.get(count).event;
                    if (indexes.containsKey(event)) {
                        indexes.get(event).add(count);
                    } else {
                        indexes.put(event, new HashSet<>() {{
                            add(count);
                        }});
                    }
                });

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
        // 空监听器
        final static Listener NULL_LISTENER = new Listener() {
        };
        final Plugin plugin;
        // 需要监听的事件 / Event to listen.
        final Class<T> event;
        // 事件链引用 / Event chain reference.
        final ChainEventRegister chain;
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

        // 过滤 / Filter.
        List<Predicate<T>> filters = new ArrayList<>();

        @Override
        @SuppressWarnings("unchecked")
        public void register() {
            EventExecutor executor;
            if (chain.entity()) {
                if (chain.schedules == null) {
                    chain.schedules = new HashMap<>();
                }

                executor = (l, e) -> {
                    // 过滤 / Filter.
                    if (filters.stream().anyMatch(f -> !f.test((T) e))) {
                        return;
                    }
                    // 判断该事件是否注销 / Check if the event is cancelled.
                    // 判断事件是否在索引中 / Check if the event is in the index.
                    if (cancel ||
                            !chain.indexes.containsKey(e.getClass()) ||
                            !(e instanceof EntityEvent)
                    ) {
                        e.getHandlers().unregister(l);
                        return;
                    }

                    // 获取进度 / Get the progress.
                    int progress = chain.schedules.getOrDefault(
                            ((EntityEvent) e).getEntity().getUniqueId(),
                            0
                    );

                    // 获取索引 / Get the index.
                    chain.indexes.get(e.getClass()).stream()
                            // 过滤掉不符合条件的事件 / Filter out the event that does not meet the condition.
                            .filter(index -> progress == 0 || progress == index - 1)
                            .findFirst()
                            .ifPresent(index -> Optional
                                    .ofNullable(chain.schedules)
                                    .ifPresent(map -> chain.schedules.put(
                                            ((EntityEvent) e).getEntity().getUniqueId(),
                                            listener.test((T) e) ? index : 0
                                    )));
                };
            } else {
                executor = (l, e) -> {
                    // 过滤 / Filter.
                    if (filters.stream().anyMatch(f -> !f.test((T) e))) {
                        return;
                    }
                    if (cancel ||
                            !chain.indexes.containsKey(e.getClass())
                    ) {
                        e.getHandlers().unregister(l);
                        return;
                    }

                    // 获取索引 / Get the index.
                    chain.indexes.get(e.getClass()).stream()
                            // 检查是否需要复位进度 / Check if the progress needs to be reset.
                            .filter(index -> chain.schedule == 0 || chain.schedule == index - 1)
                            .findFirst()
                            // 设置进度 / Set the progress.
                            // listener.test((T) e) 返回 false 表示需要复位进度
                            // listener.test((T) e) return false if the progress needs to be reset.
                            .ifPresent(index -> chain.schedule = listener.test((T) e) ? index : 0);
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
