package com.alkaidmc.alkaid.inventory;

public class AlkaidInventory {
    public BookInventory book() {
        return new BookInventory();
    }
    public GuiInventory gui() {
        return new GuiInventory();
    }
}
