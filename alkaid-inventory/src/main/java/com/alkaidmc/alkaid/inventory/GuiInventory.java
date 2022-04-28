package com.alkaidmc.alkaid.inventory;

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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class GuiInventory {
    final JavaPlugin plugin;
    static final Listener LISTENER = new Listener() {
    };

    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String title;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    int rows;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    InventoryHolder holder;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    boolean drag = false;

    // 存储物品
    List<ItemStack> items = new ArrayList<>(54);
    // 存储点击关系
    List<Consumer<InventoryClickEvent>> actions = new ArrayList<>(54);

    public GuiInventory(JavaPlugin plugin) {
        this.plugin = plugin;
        // 填充数组
        IntStream.range(0, 54).forEach(count -> {
            items.add(null);
            actions.add(null);
        });
    }

    // 操作函数
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<InventoryOpenEvent> open = null;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Consumer<InventoryCloseEvent> close = null;

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

    public GuiInventory click(Consumer<InventoryClickEvent> click, int... slots) {
        Arrays.stream(slots).forEach(s -> actions.add(s, click));
        return this;
    }

    public Inventory create() {
        // 判断 holder 是否为 null
        Optional.ofNullable(holder).or(() -> {
            Bukkit.getLogger().severe(String.format("%s, %s.",
                    "param holder (InventoryHolder) should not null",
                    "use holder(InventoryHolder holder) method add it"
            ));
            throw new NullPointerException();
        });

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
        Optional.ofNullable(open).ifPresent(event -> {
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
        Optional.ofNullable(open).ifPresent(event -> {
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
        });

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
