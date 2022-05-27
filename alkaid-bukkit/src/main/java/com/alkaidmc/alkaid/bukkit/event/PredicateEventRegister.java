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
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <p> zh </p>
 * 这是一个谓词事件注册器 可以注册一个带有 filter 过滤器的事件 <br>
 * 与 {@link SimpleEventRegister} 的区别是可以使用 @{@link #filter} 添加谓词过滤器 <br>
 * 当过滤器<b>返回 true 时</b>才会继续执行<br>
 * 注意是<b>返回 true 不是 false </b>因为 filter 代表是否需要过滤出来
 * 它和{@link java.util.stream.Stream} 的 filter 方法类似 <br>
 * {@link #unregister()} 方法调用后将从 Bukkit 中取消监听 <br>
 * <p> en </p>
 * This is a predicate event register can register a event with filter. <br>
 * Compared with {@link SimpleEventRegister} is that you can use @{@link #filter} to add predicate filter. <br>
 * When the filter <b>return true</b> will continue to execute <br>
 * Note that <b>return true is not false</b> because filter represents whether need to filter out. <br>
 * it is similar to {@link java.util.stream.Stream} filter method <br>
 * {@link #unregister()} method will call after unregister from Bukkit <br>
 *
 * @param <T> 事件类型 / Event type
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class PredicateEventRegister<T extends Event> implements AlkaidEventRegister {
    final Plugin plugin;
    // 需要监听的事件 / Event to listen.
    final Class<T> event;

    // 事件处理器 / Event handler.
    Consumer<T> listener;
    // Bukkit 事件优先级 / Bukkit event priority.
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志 / Whether to ignore Bukkit event cancellation flag.
    boolean ignore = false;

    // 注销事件标志 / Unregister event flag.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean cancel = false;

    // 过滤器 / Filter.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    List<Predicate<T>> filters = new ArrayList<>();

    /**
     * 添加过滤器 / Add filter.
     * @param filter 谓词过滤器 / Predicate filter
     * @return 返回当前对象 / Return current object
     */
    public PredicateEventRegister<T> filter(Predicate<T> filter) {
        filters.add(filter);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void register() {
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
                    // 过滤 / Filter.
                    if (filters.stream().anyMatch(filter -> !filter.test((T) e))) {
                        return;
                    }
                    listener.accept((T) e);
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
