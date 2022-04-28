package com.alkaidmc.alkaid.inventory;

import org.bukkit.inventory.InventoryHolder;

public class AlkaidInventory {
    public BookInventory book() {
        return new BookInventory();
    }

    public GuiInventory gui(int rows, InventoryHolder holder) {
        return new GuiInventory(rows, holder);
    }
}
