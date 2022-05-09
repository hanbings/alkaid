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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

/**
 * 这一个注册器是计数器事件注册器 <br>
 * 它可以按一定次数监听某一个事件 <br>
 * 计数器开始前会触发一次 before 回调 <br>
 * 当计数器到达指定次数后会触发一次 after 回调 然后对该事件的监听将被挂起 <br>
 * unregister 方法调用后将从 Bukkit 中取消监听 <br>
 *
 * @param <T> 事件类型
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class CountEventRegister<T extends Event> implements AlkaidEventRegister {
    final JavaPlugin plugin;
    // 需要监听的事件
    final Class<T> event;

    // 事件处理器
    Consumer<T> listener;
    // 事件剩余次数
    int count = 0;
    // 开始监听前的调用
    AlkaidEventCallback before = null;
    // 停止监听后的调用
    AlkaidEventCallback after = null;
    // Bukkit 事件优先级
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志
    boolean ignore = false;

    // 事件是否挂起
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean hangup = false;
    // 注销事件
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean cancel = false;

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
        this.reset();
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
                    // 检查事件是否已经被挂起
                    if (hangup) {
                        return;
                    }
                    // count 不为 0 不小于 0 即继续运行
                    if (!(count > 0)) {
                        this.hangup();
                        return;
                    }
                    listener.accept((T) e);
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
