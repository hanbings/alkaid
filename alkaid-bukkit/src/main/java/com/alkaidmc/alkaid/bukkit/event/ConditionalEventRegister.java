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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 *
 * @param <T> 事件类型 / Event type
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class ConditionalEventRegister<T extends Event> implements AlkaidEventRegister {
    final JavaPlugin plugin;
    // 需要监听的事件
    final Class<T> event;

    // 开始条件的事件
    Class<? extends Event> commence;
    // 结束条件的事件
    Class<? extends Event> interrupt;
    // 事件处理器
    Consumer<T> listener;
    // Bukkit 事件优先级
    EventPriority priority = EventPriority.NORMAL;
    // 是否忽略  Bukkit 事件的取消标志
    boolean ignore = false;
    // 是否区分玩家监听事件
    boolean multi = false;

    // 事件是否挂起
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean hangup = false;
    // 区分玩家的监听事件
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    Set<UUID> players;
    // 注销事件
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean cancel = false;

    @Override
    @SuppressWarnings("unchecked")
    public void register() {
        EventExecutor executor;
        // 根据是否区分玩家监听事件设置处理器 / Set the handler according to the multiplayer flag.
        if (multi) {
            players = new HashSet<>();
            executor = (l, e) -> {
                // 判断该事件是否注销 / Check if the event is canceled.
                if (cancel) {
                    e.getHandlers().unregister(l);
                    return;
                }
                // 检查事件是否已经被挂起 / Check if the event is hangup.
                if (e instanceof PlayerEvent) {
                    if (players.contains(((PlayerEvent) e).getPlayer().getUniqueId())) {
                        return;
                    }
                }
                listener.accept((T) e);
            };
        } else {
            executor = (l, e) -> {
                if (cancel) {
                    e.getHandlers().unregister(l);
                    return;
                }
                if (hangup) {
                    return;
                }
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
        plugin.getServer().getPluginManager().registerEvent(
                interrupt, new Listener() {
                },
                priority,
                multi ?
                        (l, e) -> {
                            if (cancel) {
                                e.getHandlers().unregister(l);
                                return;
                            }

                            if (e instanceof PlayerEvent) {
                                players.add(((PlayerEvent) e).getPlayer().getUniqueId());
                            }
                        } :
                        (l, e) -> {
                            if (cancel) {
                                e.getHandlers().unregister(l);
                                return;
                            }

                            hangup = true;
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
