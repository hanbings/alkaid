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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Consumer;

@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class ItemStackReactive {
    static final Listener LISTENER = new Listener() {
    };
    static HashMap<ItemStack, ItemStackReactive> reactive = new HashMap<>();
    static boolean registered = false;

    final @NotNull ItemStack item;
    final @NotNull Plugin plugin;

    // builder
    Consumer<PlayerInteractEvent> click;
    Consumer<BlockPlaceEvent> place;

    public static ItemStackReactive of(@NotNull ItemStack item, @NotNull Plugin plugin) {
        return new ItemStackReactive(item, plugin);
    }

    public static void remove(@NotNull ItemStack item) {
        reactive.remove(item);
    }

    public void apply() {
        if (!registered) {
            registered = true;
            plugin.getServer().getPluginManager().registerEvent(PlayerInteractEvent.class, LISTENER, EventPriority.NORMAL, (l, e) -> {
                PlayerInteractEvent event = (PlayerInteractEvent) e;

                if (event.getItem() == null) return;
                if (!reactive.containsKey(event.getItem())) return;

                ItemStackReactive reactive = ItemStackReactive.reactive.get(event.getItem());
                if (reactive.click != null) reactive.click.accept(event);

            }, plugin, false);

            plugin.getServer().getPluginManager().registerEvent(BlockPlaceEvent.class, LISTENER, EventPriority.NORMAL, (l, e) -> {
                BlockPlaceEvent event = (BlockPlaceEvent) e;

                if (!reactive.containsKey(event.getItemInHand())) return;

                ItemStackReactive reactive = ItemStackReactive.reactive.get(event.getItemInHand());
                if (reactive.place != null) reactive.place.accept(event);

            }, plugin, false);
        }

        reactive.put(item.clone(), this);
    }

    public void remove() {
        reactive.remove(item);
    }
}
