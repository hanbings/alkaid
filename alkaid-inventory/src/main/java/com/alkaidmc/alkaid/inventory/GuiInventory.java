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

package com.alkaidmc.alkaid.inventory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class GuiInventory {
    static final Listener LISTENER = new Listener() {
    };
    Plugin plugin;
    String title;
    int rows;
    InventoryHolder holder;
    boolean drag = false;
    // 存储物品
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    List<ItemStack> items = new ArrayList<>(54);
    // 存储点击关系
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    List<Consumer<InventoryClickEvent>> actions = new ArrayList<>(54);

    // 操作函数
    Consumer<InventoryOpenEvent> open = null;
    Consumer<InventoryCloseEvent> close = null;

    public GuiInventory(Plugin plugin) {
        this.plugin = plugin;
        // 填充数组
        IntStream.range(0, 54).forEach(count -> {
            items.add(null);
            actions.add(null);
        });
    }

    public GuiInventory item(ItemStack item, int... slots) {
        Arrays.stream(slots).forEach(s -> items.set(s, item));
        return this;
    }

    public GuiInventory free(ItemStack item) {
        for (int count = 0; count < rows * 9; count++) {
            items.set(count, item);
        }
        return this;
    }

    public GuiInventory click(Consumer<InventoryClickEvent> click, int... slots) {
        Arrays.stream(slots).forEach(s -> actions.set(s, click));
        return this;
    }

    public Inventory inventory() {
        // 创建 Inventory
        Inventory inventory = Bukkit.createInventory(holder, rows * 9, title);

        // 设置开关 gui 事件
        Optional.ofNullable(open).ifPresent(event -> {
            Bukkit.getPluginManager().registerEvent(
                    InventoryOpenEvent.class, LISTENER, EventPriority.LOWEST,
                    (l, e) -> {
                        InventoryOpenEvent action = (InventoryOpenEvent) e;
                        if (Objects.equals(action.getInventory().getHolder(), holder)) {
                            open.accept(action);
                        }
                    }, plugin
            );
        });
        Optional.ofNullable(close).ifPresent(event -> {
            Bukkit.getPluginManager().registerEvent(
                    InventoryCloseEvent.class, LISTENER, EventPriority.LOWEST,
                    (l, e) -> {
                        InventoryCloseEvent action = (InventoryCloseEvent) e;
                        if (Objects.equals(action.getInventory().getHolder(), holder)) {
                            close.accept(action);
                        }
                    }, plugin
            );
        });

        // 设置点击事件
        Bukkit.getPluginManager().registerEvent(
                InventoryClickEvent.class, LISTENER, EventPriority.LOWEST,
                (l, e) -> {
                    InventoryClickEvent action = (InventoryClickEvent) e;
                    Optional.ofNullable(action.getClickedInventory()).ifPresent(inv -> {
                        if (Objects.equals(action.getClickedInventory().getHolder(), holder)) {
                            Optional.ofNullable(actions.get(action.getSlot())).ifPresent(a -> a.accept(action));
                        }
                    });
                }, plugin
        );

        // 设置拖拽事件
        Bukkit.getPluginManager().registerEvent(
                InventoryDragEvent.class, LISTENER, EventPriority.LOWEST,
                (l, e) -> {
                    InventoryDragEvent action = (InventoryDragEvent) e;
                    if (Objects.equals(action.getInventory().getHolder(), holder)) {
                        action.setCancelled(true);
                    }
                }, plugin);

        // 设置物品
        IntStream.range(0, rows * 9)
                .forEach(count -> Optional.ofNullable(items.get(count))
                        .ifPresent(i -> inventory.setItem(count, i)));

        // 返回 Inventory
        return inventory;
    }
}
