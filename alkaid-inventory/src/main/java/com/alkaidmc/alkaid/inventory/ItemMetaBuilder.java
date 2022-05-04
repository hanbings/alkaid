/*
 * Copyright 2022 Alkaid
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

import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor
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
    @SuppressWarnings("SpellCheckingInspection")
    List<String> lores = new ArrayList<>();
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Map<Enchantment, Integer> enchantments = new HashMap<>();
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Set<ItemFlag> flags = new HashSet<>();
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    boolean unbreakable;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    int model;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Multimap<Attribute, AttributeModifier> attributes;

    // todo 默认 ItemMeta 设置
    ItemMeta meta;

    public ItemMetaBuilder(ItemMeta meta) {
        this.meta = meta;
    }

    public ItemMetaBuilder(Material material) {
        this.meta = new ItemStack(material).getItemMeta();
    }

    public ItemMetaBuilder of(ItemMeta meta) {
        this.display = meta.getDisplayName();
        this.localized = meta.getLocalizedName();
        this.lores = meta.getLore();
        this.enchantments = meta.getEnchants();
        this.flags = meta.getItemFlags();
        this.model = meta.getCustomModelData();
        this.attributes = meta.getAttributeModifiers();
        return this;
    }

    public ItemMetaBuilder of(ItemStack item) {
        return Optional.ofNullable(item.getItemMeta())
                .map(this::of)
                .orElse(this);
    }

    public ItemMetaBuilder enchantment(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemMetaBuilder flag(ItemFlag flag) {
        flags.add(flag);
        return this;
    }

    public ItemMetaBuilder lore(String... lore) {
        Collections.addAll(lores, lore);
        return this;
    }

    public ItemMetaBuilder lore(String lore) {
        lores.add(lore);
        return this;
    }

    public ItemMetaBuilder attribute(Attribute attribute, AttributeModifier modifier) {
        attributes.put(attribute, modifier);
        return this;
    }

    // todo Optional null 检测
    public ItemMeta meta() {
        meta.setDisplayName(display);
        meta.setLocalizedName(localized);
        meta.setLore(lores);
        meta.setCustomModelData(model);
        enchantments.forEach((k, v) -> meta.addEnchant(k, v, true));
        flags.forEach(meta::addItemFlags);
        attributes.forEach(meta::addAttributeModifier);
        return meta;
    }
}
