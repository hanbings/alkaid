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
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <p> zh </p>
 * 这个注册器允许注册一个事件段落 <br>
 * 它可以以某一个条件开始监听 从另一个条件结束监听 <br>
 * 也就是说它允许由头部 主体 尾部三个部分组成的事件段落 <br>
 * 使用 {@link #commence(Class)} 进入监听段落 <br>
 * 使用 {@link #interrupt(Class)} 结束监听段落 <br>
 * 调用顺序 commence 事件 -> listener 设置的事件处理器 -> interrupt 事件 <br>
 * {@link #unregister()} 方法调用后将从 Bukkit 中取消监听 <br>
 * 默认对所有玩家使用同一个段落
 * 将 {@link #multi(boolean)} 标记为 true 可以对不同玩家使用不同的段落 <br>
 * 使用 {@link #filter(Predicate)} 进行条件过滤 用法参照 {@link PredicateEventRegister} <br>
 * <p> en </p>
 * This register allows you to register a section of events. <br>
 * It can start listening from a condition and end listening from another condition. <br>
 * That is, it can be used to start listening from a header, body, and tail. <br>
 * Use {@link #commence(Class)} to enter the listening section. <br>
 * Use {@link #interrupt(Class)} to end the listening section. <br>
 * Call the order commence event -> listener's event handler -> interrupt event <br>
 * {@link #unregister()} will unregister the listener from Bukkit <br>
 * The default is to use the same section for all players
 * {@link #multi(boolean)} is marked true can use different sections for different players. <br>
 * Use {@link #filter(Predicate)} to filter conditions, Usage is the same as {@link PredicateEventRegister} <br>
 *
 * @param <T> 事件类型 / Event type
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class SectionEventRegister<T extends Event> implements AlkaidEventRegister {
    static final Listener NULL_LISTENER = new Listener() {
    };
    final Plugin plugin;
    // 需要监听的事件 / Event to listen.
    final Class<T> event;

    // 作为段落头的事件 / Event to listen as a section head.
    Class<? extends Event> commence;
    // 作为段落尾的事件 / Event to listen as a section tail.
    Class<? extends Event> interrupt;
    // 事件处理器 / Event handler.
    Consumer<T> listener;
    // Bukkit 事件优先级 / Bukkit event priority.
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志 / Whether to ignore Bukkit event cancellation flag.
    boolean ignore = false;
    // 是否区分玩家监听事件 / Whether to distinguish player listening events.
    boolean multi = false;

    // 是否已经进入段落 / Whether to enter the section.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean schedule = false;
    // 区分玩家的监听事件 / Distinguish player listening event.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    Set<UUID> schedules;
    // 注销事件 / Unregister event flag.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean cancel = false;

    // 过滤器 / Filter.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    List<Predicate<T>> filters = new ArrayList<>();

    public SectionEventRegister<T> filter(Predicate<T> filter) {
        filters.add(filter);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void register() {
        EventExecutor executor;
        // 根据是否区分玩家监听事件设置处理器 / Set the handler according to the multiplayer flag.
        if (multi) {
            schedules = new HashSet<>();
            executor = (l, e) -> {
                // 判断该事件是否注销 / Check if the event is canceled.
                if (cancel) {
                    e.getHandlers().unregister(l);
                    return;
                }
                // 过滤 / Filter.
                if (filters.stream().anyMatch(f -> !f.test((T) e))) {
                    return;
                }
                // 检查事件是否已经被挂起 / Check if the event is hangup.
                if (e instanceof PlayerEvent) {
                    if (schedules.contains(((PlayerEvent) e).getPlayer().getUniqueId())) {
                        listener.accept((T) e);
                    }
                }
            };
        } else {
            executor = (l, e) -> {
                if (cancel) {
                    e.getHandlers().unregister(l);
                    return;
                }
                // 过滤 / Filter.
                if (filters.stream().anyMatch(f -> !f.test((T) e))) {
                    return;
                }
                if (schedule) {
                    listener.accept((T) e);
                }
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

        // 注册段落头事件 / Register section head event.
        plugin.getServer().getPluginManager().registerEvent(
                commence,
                NULL_LISTENER,
                priority,
                multi ?
                        (l, e) -> {
                            if (cancel) {
                                e.getHandlers().unregister(l);
                                return;
                            }

                            if (e instanceof PlayerEvent) {
                                schedules.add(((PlayerEvent) e).getPlayer().getUniqueId());
                            }
                        } :
                        (l, e) -> {
                            if (cancel) {
                                e.getHandlers().unregister(l);
                                return;
                            }

                            schedule = true;
                        },
                plugin,
                ignore
        );

        // 注册段落尾事件 / Register section tail event.
        plugin.getServer().getPluginManager().registerEvent(
                interrupt,
                NULL_LISTENER,
                priority,
                multi ?
                        (l, e) -> {
                            if (cancel) {
                                e.getHandlers().unregister(l);
                                return;
                            }

                            if (e instanceof PlayerEvent) {
                                schedules.remove(((PlayerEvent) e).getPlayer().getUniqueId());
                            }
                        } :
                        (l, e) -> {
                            if (cancel) {
                                e.getHandlers().unregister(l);
                                return;
                            }

                            schedule = false;
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
