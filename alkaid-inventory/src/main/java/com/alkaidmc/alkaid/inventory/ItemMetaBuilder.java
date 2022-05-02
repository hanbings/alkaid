package com.alkaidmc.alkaid.inventory;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class ItemMetaBuilder {
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String display;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String localized;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    List<String> lore = new ArrayList<>();
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Map<Enchantment, Integer> enchantments = new HashMap<>();
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Set<ItemFlag> flags = new HashSet<>();

    public ItemMetaBuilder inject(ItemMeta meta) {
        this.display = meta.getDisplayName();
        this.localized = meta.getLocalizedName();
        this.lore = meta.getLore();
        this.enchantments = meta.getEnchants();
        this.flags = meta.getItemFlags();
        return this;
    }

    public void enchantment(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
    }
}
