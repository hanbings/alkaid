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

import com.alkaidmc.alkaid.bukkit.event.interfaces.AlkaidEventCallback;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <p> zh ver. </p>
 * 这一个注册器是计数器事件注册器 <br>
 * 它可以按一定次数监听某一个事件 <br>
 * 计数器开始前会触发一次 {@link #before(AlkaidEventCallback)} 回调 <br>
 * 当计数器到达指定次数后会触发一次 {@link #after(AlkaidEventCallback)} 回调 然后对该事件的监听将被挂起 <br>
 * {@link #unregister()} 方法调用后将从 Bukkit 中取消监听 <br>
 * 默认的情况下计数器将监听所有被触发的事件
 * 如果需要针对玩家区分监听 则需要将 {@link #multi(boolean)} 方法标记为 true <br>
 * 使用 {@link #filter(Predicate)} 进行条件过滤 用法参照 {@link PredicateEventRegister} <br>
 * <p> en ver. </p>
 * This is a counter event register. <br>
 * It can listen to a specific event by a certain times. <br>
 * It will trigger a {@link #before(AlkaidEventCallback)} callback when the counter is started. <br>
 * It will trigger a {@link #after(AlkaidEventCallback)} callback when the counter is reached.
 * The listener of this event will be suspended after the counter is reached. <br>
 * {@link #unregister()} method will cancel the listener from Bukkit. <br>
 * The default situation is that the counter will listen to all triggered events.
 * If you need to differentiate the listener between players, you need to mark {@link #multi(boolean)} as true. <br>
 * Use {@link #filter(Predicate)} to filter conditions, Usage is the same as {@link PredicateEventRegister} <br>
 *
 * @param <T> 事件类型 / Event type
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class CountEventRegister<T extends Event> implements AlkaidEventRegister {
    final Plugin plugin;
    // 需要监听的事件 / Event to listen.
    final Class<T> event;

    // 事件处理器 / Event handler.
    Consumer<T> listener;
    // 事件剩余次数 / Event remaining times.
    int count = 0;
    // 开始监听前的调用 / Callback before listening.
    AlkaidEventCallback before = null;
    // 停止监听后的调用 / Callback after stopping.
    AlkaidEventCallback after = null;
    // Bukkit 事件优先级 / Bukkit event priority.
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志 / Whether to ignore Bukkit event cancellation flag.
    boolean ignore = false;
    // 是否区分玩家监听事件 / Whether to differentiate player listener event.
    boolean multi = false;

    // 事件是否挂起 / Whether the event is suspended.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean hangup = false;
    // 区分玩家的监听事件 / Differentiate player listener event.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    HashMap<UUID, Integer> hangups;
    // 注销事件 / Unregister event.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean cancel = false;

    // 过滤器 / Filter.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    List<Predicate<T>> filters = new ArrayList<>();

    public CountEventRegister<T> filter(Predicate<T> filter) {
        filters.add(filter);
        return this;
    }

    public void reset() {
        this.before.callback(plugin, this);
        this.hangup = false;
    }

    public void hangup() {
        this.after.callback(plugin, this);
        this.hangup = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void register() {
        // 初始化事件处理器 / Initialize event handler.
        EventExecutor executor;

        if (multi) {
            hangups = new HashMap<>();
            executor = (l, e) -> {
                // 过滤 / Filter.
                if (filters.stream().anyMatch(f -> !f.test((T) e))) {
                    return;
                }
                // 判断该事件是否注销 / Check if the event is cancelled.
                if (cancel) {
                    e.getHandlers().unregister(l);
                    return;
                }
                // 检查玩家的事件计数是否已经等于 0
                // Check if the player's event count is equal to 0.
                if (!(e instanceof PlayerEvent)) {
                    return;
                }
                UUID uuid = ((PlayerEvent) e).getPlayer().getUniqueId();
                // 判断是否存在该玩家的计数 没有则初始化 存在但为 0 不执行
                // counter is not exist, then initialize it. if the counter is existed and is 0, then do not execute.
                if (!hangups.containsKey(uuid)) {
                    listener.accept((T) e);
                    hangups.put(uuid, count - 1);
                }
                if (hangups.get(uuid) != 0) {
                    listener.accept((T) e);
                    hangups.put(uuid, hangups.get((uuid)) - 1);
                }
            };
        } else {
            executor = (l, e) -> {
                // 过滤 / Filter.
                if (filters.stream().anyMatch(filter -> !filter.test((T) e))) {
                    return;
                }
                if (cancel) {
                    e.getHandlers().unregister(l);
                    return;
                }
                if (hangup) {
                    return;
                }
                // count 不为 0 不小于 0 即继续运行 / count is not 0 and not less than 0, then continue running.
                if (!(count > 0)) {
                    this.hangup();
                    return;
                }
                listener.accept((T) e);
                // 执行计数 / Execute count
                count--;
            };
        }

        this.reset();
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

    @Override
    public void unregister() {
        cancel = true;
    }
}
