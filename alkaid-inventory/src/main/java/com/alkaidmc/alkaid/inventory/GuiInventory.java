package com.alkaidmc.alkaid.inventory;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class GuiInventory {
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String title;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    int rows;
}
