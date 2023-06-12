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

package com.alkaidmc.alkaid.inventory;

import lombok.*;
import lombok.experimental.Accessors;
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
import java.util.function.Function;

@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class CustomInventory {
    static final Listener LISTENER = new Listener() {
    };
    static final Map<UUID, CustomInventory> INVENTORY_REGISTRY = new HashMap<>();

    // constructor
    final Plugin plugin;

    // builder
    int rows;
    long interval;
    InventoryHolder holder;
    Consumer<InventoryOpenEvent> open;
    Consumer<InventoryCloseEvent> close;
    Consumer<InventoryClickEvent> click;
    Consumer<InventoryDragEvent> drag;
    Function<List<ItemStackAction>, List<ItemStackAction>> update;

    // instance
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Inventory inventory;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    List<ItemStack> items = new ArrayList<>();
    @Setter(AccessLevel.NONE)
    UUID uuid = UUID.randomUUID();

    public CustomInventory add(ItemStack item) {
        items.add(item);
        return this;
    }

    public CustomInventory add(ItemStackAction action, ItemStack item) {
        return this;
    }

    public CustomInventory add(ItemStackAction action, ItemStack item, int... slot) {
        return this;
    }

    public CustomInventory structure(char[][] shape, Map<Character, ItemStackRegistry> map) {
        return this;
    }

    public Inventory inventory() {
        return inventory;
    }

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    public static class ItemStackAction {
        Consumer<ItemStack> click;
        Consumer<ItemStack> left;
        Consumer<ItemStack> right;
        Consumer<ItemStack> drag;
        Consumer<ItemStack> update;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @Accessors(fluent = true, chain = true)
    public static class ItemStackRegistry {
        int slot;
        ItemStack item;
        Consumer<ItemStackAction> action;
    }
}
