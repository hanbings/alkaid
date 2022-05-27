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
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <p> zh </p>
 * 这是一个计时时间注册器，可以注册一个限时监听事件 <br>
 * 计时开始前会触发一次 {@link #before(AlkaidEventCallback)} 回调 <br>
 * 当计时结束后会触发一次 {@link #after(AlkaidEventCallback)} 回调 然后对该事件的监听将被挂起 <br>
 * {@link #unregister()} 方法调用后将从 Bukkit 中取消监听 <br>
 * <p> en </p>
 * This is a timer event register can register a time limit event. <br>
 * When the timer starts, it will trigger a {@link #before(AlkaidEventCallback)} callback. <br>
 * When the timer ends, it will trigger a {@link #after(AlkaidEventCallback)}
 * callback and the listener of this event will be suspended. <br>
 * {@link #unregister()} method call after will cancel the listener from Bukkit. <br>
 *
 * @param <T> 事件类型 / Event type
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class TimerEventRegister<T extends Event> implements AlkaidEventRegister {
    static final Timer timer = new Timer();
    final Plugin plugin;
    // 需要监听的事件 / Event to listen.
    final Class<T> event;
    // 事件处理器 / Event handler.
    Consumer<T> listener;
    // 事件剩余次数 / Event remaining times.
    int time = 0;
    // 开始监听前的调用 / Callback before listening.
    AlkaidEventCallback before = null;
    // 停止监听后的调用 / Callback after stopping.
    AlkaidEventCallback after = null;
    // Bukkit 事件优先级 / Bukkit event priority.
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志 / Whether to ignore Bukkit event cancellation flag.
    boolean ignore = false;

    // 事件是否挂起 / Whether the event is suspended.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean hangup = false;
    // 注销事件标志 / Unregister event flag.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean cancel = false;

    // 过滤器 / Filter.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    List<Predicate<T>> filters = new ArrayList<>();

    public TimerEventRegister<T> filter(Predicate<T> filter) {
        filters.add(filter);
        return this;
    }

    public void reset() {
        this.hangup = false;
        this.before.callback(plugin, this);

    }

    public void hangup() {
        this.hangup = true;
        this.after.callback(plugin, this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void register() {
        this.reset();
        plugin.getServer().getPluginManager().registerEvent(
                event,
                new Listener() {
                },
                priority,
                (l, e) -> {
                    // 判断该事件是否注销 / Check if the event is cancelled.
                    if (cancel) {
                        e.getHandlers().unregister(l);
                        return;
                    }
                    if (hangup) {
                        return;
                    }
                    // 过滤 / Filter.
                    if (filters.stream().anyMatch(filter -> !filter.test((T) e))) {
                        return;
                    }
                    listener.accept((T) e);
                },
                plugin,
                ignore
        );

        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                hangup();
            }
        }, time * 1000L);
    }

    @Override
    public void unregister() {
        cancel = true;
    }
}