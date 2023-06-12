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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class CustomInventory {
    static final Listener LISTENER = new Listener() {
    };

    // constructor
    final Plugin plugin;

    // builder
    String title = "Alkaid Custom Inventory";
    int rows = 6;
    long interval = 20;
    @Nullable Holder holder;
    @Nullable Consumer<InventoryOpenEvent> open;
    @Nullable Consumer<InventoryCloseEvent> close;
    @Nullable Consumer<InventoryClickEvent> click;
    @Nullable Consumer<InventoryDragEvent> drag;
    @Nullable Update update;

    // instance
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Inventory inventory;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    List<ItemStackRegistry> registries = new ArrayList<>();
    @Setter(AccessLevel.NONE)
    UUID uuid = UUID.randomUUID();

    public CustomInventory add(ItemStack item) {
        registries.add(new ItemStackRegistry(registries.size(), item, new ItemStackAction()));
        return this;
    }

    public CustomInventory add(ItemStackAction action, ItemStack item) {
        registries.add(new ItemStackRegistry(registries.size(), item, action));
        return this;
    }

    public CustomInventory add(ItemStackAction action, ItemStack item, int... slot) {
        for (int i : slot) registries.add(new ItemStackRegistry(i, item, action));
        return this;
    }

    public CustomInventory structure(List<String> shape, Map<Character, ItemStackRegistry> map) {
        for (int row = 0; row < shape.size(); row++) {
            String line = shape.get(row);
            for (int col = 0; col < line.length(); col++) {
                ItemStackRegistry registry = map.get(line.charAt(col));
                if (registry == null) continue;
                registry.slot(row * 9 + col);
                registries.add(registry);
            }
        }

        return this;
    }

    public Inventory inventory() {
        // create inventory
        this.holder = new Holder(this);
        this.inventory = Bukkit.createInventory(this.holder, this.rows * 9, this.title);

        // register listener

        // open
        plugin.getServer().getPluginManager().registerEvent(InventoryOpenEvent.class, LISTENER, EventPriority.NORMAL, (l, e) -> {
            if (!(((InventoryOpenEvent) e).getInventory().getHolder() instanceof Holder)) return;
            Holder holder = (Holder) inventory.getHolder();
            if (holder == null || holder.custom.uuid != uuid) return;
            if (open != null) open.accept((InventoryOpenEvent) e);
        }, plugin, false);

        // close
        plugin.getServer().getPluginManager().registerEvent(InventoryCloseEvent.class, LISTENER, EventPriority.NORMAL, (l, e) -> {
            if (!(((InventoryCloseEvent) e).getInventory().getHolder() instanceof Holder)) return;
            Holder holder = (Holder) inventory.getHolder();
            if (holder == null || holder.custom.uuid != uuid) return;
            if (close != null) close.accept((InventoryCloseEvent) e);
        }, plugin, false);

        // click
        plugin.getServer().getPluginManager().registerEvent(InventoryClickEvent.class, LISTENER, EventPriority.NORMAL, (l, e) -> {
            if (!(((InventoryClickEvent) e).getInventory().getHolder() instanceof Holder)) return;
            Holder holder = (Holder) inventory.getHolder();
            if (holder == null || holder.custom.uuid != uuid) return;
            if (click != null) click.accept((InventoryClickEvent) e);

            // item click callback
            int slot = ((InventoryClickEvent) e).getSlot();
            registries.forEach(r -> {
                if (!(slot == r.slot)) return;

                // click
                if (r.action.click() != null) r.action().click().accept(inventory.getItem(slot));
                // left
                if (r.action().left() != null && ((InventoryClickEvent) e).isLeftClick())
                    r.action().left().accept(inventory.getItem(slot));
                // right
                if (r.action.right != null && ((InventoryClickEvent) e).isRightClick())
                    r.action().right().accept(inventory.getItem(slot));
            });

            // fill inventory
            registries.forEach(r -> {
                if (!(r.slot == slot)) return;
                inventory.setItem(slot, r.item);
            });

        }, plugin, false);

        // drag
        plugin.getServer().getPluginManager().registerEvent(InventoryDragEvent.class, LISTENER, EventPriority.NORMAL, (l, e) -> {
            if (!(((InventoryDragEvent) e).getInventory().getHolder() instanceof Holder)) return;
            Holder holder = (Holder) inventory.getHolder();
            if (holder == null || holder.custom.uuid != uuid) return;
            if (drag != null) drag.accept((InventoryDragEvent) e);

            // item drag callback
            int slot = ((InventoryDragEvent) e).getRawSlots().stream().findFirst().orElse(-1);
            registries.forEach(r -> {
                if (!(slot == r.slot)) return;

                // drag
                if (r.action().drag() != null) r.action().drag().accept(inventory.getItem(slot));
            });
        }, plugin, false);

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
        ItemStackAction action;
    }


    @Setter
    @Getter
    @AllArgsConstructor
    @Accessors(fluent = true, chain = true)
    public static class Holder implements InventoryHolder {
        CustomInventory custom;

        @Override
        public @NonNull Inventory getInventory() {
            return custom.inventory;
        }
    }

    @FunctionalInterface
    public interface Update {
        List<ItemStackRegistry> update(CustomInventory custom, Inventory inventory, List<ItemStackRegistry> registries);
    }
}
