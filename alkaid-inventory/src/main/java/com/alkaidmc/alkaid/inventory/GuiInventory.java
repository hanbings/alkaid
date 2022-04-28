package com.alkaidmc.alkaid.inventory;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class GuiInventory {
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String title;
    @Getter
    @Accessors(fluent = true, chain = true)
    final int rows;
    @Getter
    @Accessors(fluent = true, chain = true)
    final InventoryHolder holder;

    public GuiInventory(int rows, InventoryHolder holder) {
        this.rows = rows;
        this.holder = holder;
        // 扩容数组
        IntStream.range(0, rows * 9).forEach(count -> {
            items.add(null);
            actions.add(null);
        });
    }

    // 存储物品
    List<ItemStack> items = new ArrayList<>();
    // 存储点击关系
    List<Consumer<InventoryClickEvent>> actions = new ArrayList<>();

    public GuiInventory item(ItemStack item, int... slots) {
        Arrays.stream(slots).forEach(s -> items.add(s, item));
        return this;
    }

    public GuiInventory free(ItemStack item) {
        for (int count = 0; count < rows * 9; count++) {
            items.add(count, item);
        }
        return this;
    }

    public GuiInventory open(Consumer<InventoryOpenEvent> open) {
        return this;
    }

    public GuiInventory click(Consumer<InventoryClickEvent> click, int... slots) {
        return this;
    }

    public GuiInventory close(Consumer<InventoryCloseEvent> close) {
        return this;
    }

    public Inventory create() {
        return null;
    }
}
