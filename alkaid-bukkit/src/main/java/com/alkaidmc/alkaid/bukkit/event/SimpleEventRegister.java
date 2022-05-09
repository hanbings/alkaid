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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

/**
 * 这是一个简单的事件注册器 可以注册一个事件 <br>
 * 提供优先级与忽略取消标记位的设置 <br>
 *
 * @param <T> 事件类型
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class SimpleEventRegister<T extends Event> implements AlkaidEventRegister {
    final JavaPlugin plugin;
    // 需要监听的事件
    final Class<T> event;

    // 事件处理器
    Consumer<T> listener;
    // Bukkit 事件优先级
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志
    boolean ignore = false;

    // 注销事件标志
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean cancel = false;

    @Override
    @SuppressWarnings("unchecked")
    public void register() {
        plugin.getServer().getPluginManager().registerEvent(
                event,
                new Listener() {
                },
                priority,
                (l, e) -> {
                    // 判断该事件是否注销
                    if (cancel) {
                        e.getHandlers().unregister(l);
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
