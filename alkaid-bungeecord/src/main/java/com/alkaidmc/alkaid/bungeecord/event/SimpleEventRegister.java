/*
 * Copyright 2023 Alkaid
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

package com.alkaidmc.alkaid.bungeecord.event;

import com.alkaidmc.alkaid.bungeecord.event.interfaces.AlkaidEventRegister;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventPriority;

import java.util.function.Consumer;

/**
 * <p> zh </p>
 * 这是一个简单的事件注册器 可以注册一个事件 <br>
 * 提供优先级与忽略取消标记位的设置 <br>
 * {@link #unregister()} 方法调用后将从 Bungee cord 中取消监听 <br>
 * <p> en </p>
 * This is a simple event register can register a event. <br>
 * Provide priority and ignore cancel flag setting. <br>
 * {@link #unregister()} method will call after unregister from Bungee cord <br>
 *
 * @param <T> 事件类型 / Event type
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class SimpleEventRegister<T extends Event> implements AlkaidEventRegister {
    final Plugin plugin;
    // 需要监听的事件 / Event to listen.
    final Class<T> event;

    // 事件处理器 / Event handler.
    Consumer<T> listener;
    // Bukkit 事件优先级 / Bukkit event priority.
    byte priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志 / Whether to ignore Bukkit event cancellation flag.
    boolean ignore = false;

    // 注销事件标志 / Unregister event flag.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean cancel = false;

    @Override
    public void register() {
        try {
            AlkaidEvent.registerEvent(
                    plugin,
                    event,
                    priority,
                    this.getClass(),
                    e -> {
                        if (cancel) {
                            // todo: unregister event
                            return;
                        }
                        listener.accept(e);
                    });
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unregister() {
        cancel = true;
    }
}
