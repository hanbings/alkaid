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
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
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
    @Nullable CustomInventory.InventoryUpdate update;

    // instance
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    Inventory inventory;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    List<ItemStackRegistry> registries = new ArrayList<>();
    @Setter(AccessLevel.NONE)
    UUID uuid = UUID.randomUUID();
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    BukkitTask updater;

    public CustomInventory add(ItemStack item) {
        registries.add(new ItemStackRegistry(registries.size(), item, new ItemStackAction()));
        return this;
    }

    public CustomInventory add(ItemStackAction action, ItemStack item) {
        registries.add(new ItemStackRegistry(registries.size(), item, action));
        return this;
    }

    public CustomInventory add(ItemStackAction action, ItemStack item, int... rawSlots) {
        for (int i : rawSlots) registries.add(new ItemStackRegistry(i, item, action));
        return this;
    }

    public CustomInventory structure(List<String> shape, Map<Character, ItemStackRegister> map) {
        this.rows = shape.size();

        final Map<Character, AtomicInteger> indexMap = new HashMap<>();

        for (int row = 0; row < shape.size(); row++) {
            String line = shape.get(row);
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) == ' ') continue;

                ItemStackRegister register = map.get(line.charAt(col));
                if (register == null) continue;

                int slot = row * 9 + col;

                if (!indexMap.containsKey(line.charAt(col))) {
                    indexMap.put(line.charAt(col), new AtomicInteger());
                }
                int index = indexMap.get(line.charAt(col)).getAndIncrement();

                ItemStackRegistry registry = new ItemStackRegistry(
                        slot, register.item.apply(slot, index).clone(), register.action.apply(slot, index)
                );

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
            InventoryOpenEvent event = (InventoryOpenEvent) e;
            if (!(event.getInventory().getHolder() instanceof Holder)) return;
            if (((Holder) event.getInventory().getHolder()).custom() != this) return;

            Holder holder = (Holder) inventory.getHolder();
            if (holder == null || holder.custom.uuid != uuid) return;
            if (open != null) open.accept(event);

            // update
            // using bukkit scheduler
            if (update != null) {
                updater = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                    if (holder.custom.uuid != uuid) return;
                    if (event.getPlayer() instanceof Player) return;

                    List<ItemStackRegistry> relist = update.update(this, inventory, registries);
                    registries.forEach(registry -> registry.action.update.update(
                            this,
                            inventory,
                            registries,
                            registry,
                            (Player) event.getPlayer()
                    ));

                    if (relist != null) {
                        registries = relist;
                        registries.forEach(registry -> inventory.setItem(registry.slot, registry.item));
                    }
                }, 0, interval);
            }

        }, plugin, false);

        // close
        plugin.getServer().getPluginManager().registerEvent(InventoryCloseEvent.class, LISTENER, EventPriority.NORMAL, (l, e) -> {
            InventoryCloseEvent event = (InventoryCloseEvent) e;
            if (!(event.getInventory().getHolder() instanceof Holder)) return;
            if (((Holder) event.getInventory().getHolder()).custom() != this) return;

            Holder holder = (Holder) inventory.getHolder();
            if (holder == null || holder.custom.uuid != uuid) return;
            if (close != null) close.accept(event);

            // stop upload
            if (updater != null) updater.cancel();

        }, plugin, false);

        // click
        plugin.getServer().getPluginManager().registerEvent(InventoryClickEvent.class, LISTENER, EventPriority.NORMAL, (l, e) -> {
            InventoryClickEvent event = (InventoryClickEvent) e;
            if (!(event.getInventory().getHolder() instanceof Holder)) return;
            if (((Holder) event.getInventory().getHolder()).custom() != this) return;

            Holder holder = (Holder) inventory.getHolder();
            if (holder == null || holder.custom.uuid != uuid) return;
            if (click != null) click.accept(event);

            // item click callback
            int rawSlot = (event.getRawSlot());
            registries.forEach(registry -> {
                if (!(rawSlot == registry.slot)) return;

                // click
                if (registry.action.click != null)
                    registry.action.click.update(this, registries, event);
                // left
                if (registry.action.left != null && (event.isLeftClick()))
                    registry.action.left.update(this, registries, event);
                // right
                if (registry.action.right != null && (event.isRightClick()))
                    registry.action.right.update(this, registries, event);
            });
        }, plugin, false);

        // drag
        plugin.getServer().getPluginManager().registerEvent(InventoryDragEvent.class, LISTENER, EventPriority.NORMAL, (l, e) -> {
            InventoryDragEvent event = (InventoryDragEvent) e;
            if (!(event.getInventory().getHolder() instanceof Holder)) return;
            if (((Holder) event.getInventory().getHolder()).custom() != this) return;

            Holder holder = (Holder) inventory.getHolder();
            if (holder == null || holder.custom.uuid != uuid) return;
            if (drag != null) drag.accept(event);

            // item drag callback
            int rawSlot = (event.getRawSlots().stream().findFirst().orElse(-1));
            registries.forEach(registry -> {
                if (!(rawSlot == registry.slot)) return;

                // drag
                if (registry.action.drag != null)
                    registry.action.drag.update(this, registries, event);
            });
        }, plugin, false);

        // fill inventory
        registries.forEach(registry -> inventory.setItem(registry.slot(), registry.item()));

        return inventory;
    }

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    public static class ItemStackAction {
        ItemStackActivity<InventoryClickEvent> click;
        ItemStackActivity<InventoryClickEvent> left;
        ItemStackActivity<InventoryClickEvent> right;
        ItemStackActivity<InventoryDragEvent> drag;
        CustomInventory.ItemStackUpdate update;
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
    public static class ItemStackRegister {
        BiFunction<Integer, Integer, ItemStack> item;
        BiFunction<Integer, Integer, ItemStackAction> action;

        public ItemStackRegister(ItemStack itemStack, ItemStackAction action) {
            this((slot, index) -> itemStack, (slot, index) -> action);
        }
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
    public interface InventoryUpdate {
        List<ItemStackRegistry> update(CustomInventory custom, Inventory inventory, List<ItemStackRegistry> registries);
    }

    @FunctionalInterface
    public interface ItemStackUpdate {
        void update(CustomInventory custom, Inventory inventory, List<ItemStackRegistry> registries, ItemStackRegistry registry, Player player);
    }

    @FunctionalInterface
    public interface ItemStackActivity<T extends InventoryEvent> {
        void update(CustomInventory custom, List<ItemStackRegistry> registries, T event);
    }
}
