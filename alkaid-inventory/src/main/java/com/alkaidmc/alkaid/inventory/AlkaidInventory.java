package com.alkaidmc.alkaid.inventory;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public class AlkaidInventory {
    final JavaPlugin plugin;

    public BookInventory book() {
        return new BookInventory();
    }

    public GuiInventory gui() {
        return new GuiInventory(this.plugin);
    }
}
