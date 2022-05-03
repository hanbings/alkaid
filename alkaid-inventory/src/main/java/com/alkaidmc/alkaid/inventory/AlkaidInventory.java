package com.alkaidmc.alkaid.inventory;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AlkaidInventory {
    final JavaPlugin plugin;

    public BookInventory book() {
        return new BookInventory();
    }

    public GuiInventory gui() {
        return new GuiInventory(plugin);
    }

    public ItemStackBuilder item() {
        return new ItemStackBuilder();
    }
}
