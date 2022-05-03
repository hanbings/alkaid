package com.alkaidmc.alkaid.inventory;

import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AlkaidInventory {
    final JavaPlugin plugin;

    public BookInventory book() {
        return new BookInventory();
    }

    public GuiInventory gui(int rows, InventoryHolder holder) {
        return new GuiInventory(this.plugin, rows, holder);
    }

    public ItemStackBuilder item() {
        return new ItemStackBuilder();
    }
}
