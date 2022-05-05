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
import lombok.AccessLevel;
import lombok.Getter;
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

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class ItemMetaBuilder {
    // 默认 ItemMeta / default ItemMeta.
    final static ItemMeta DEFAULT_META = new ItemStack(Material.STONE).getItemMeta();

    String display;
    String localized;
    @SuppressWarnings("SpellCheckingInspection")
    List<String> lores = new ArrayList<>();
    Map<Enchantment, Integer> enchantments = new HashMap<>();
    Set<ItemFlag> flags = new HashSet<>();
    boolean unbreakable;
    int model;
    Multimap<Attribute, AttributeModifier> attributes;

    // 如果没有指定 ItemMeta，则使用默认 ItemMeta / if no ItemMeta, use default ItemMeta.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    ItemMeta meta = DEFAULT_META;

    public ItemMetaBuilder(ItemMeta meta) {
        this.meta = meta;
    }

    public ItemMetaBuilder(Material material) {
        this.meta = new ItemStack(material).getItemMeta();
    }

    public ItemMetaBuilder of(ItemMeta meta) {
        // 从 meta 中获取属性 / get attributes from meta.
        this.meta = meta;
        // 设置值 / set value.
        this.display = meta.getDisplayName();
        this.localized = meta.getLocalizedName();
        this.lores = meta.getLore();
        this.enchantments = meta.getEnchants();
        this.flags = meta.getItemFlags();
        this.model = meta.getCustomModelData();
        this.attributes = meta.getAttributeModifiers();
        this.unbreakable = meta.isUnbreakable();
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

    public ItemMeta meta() {
        // 进行 null 检测 / check null.
        Optional.ofNullable(display).ifPresent(meta::setDisplayName);
        Optional.ofNullable(localized).ifPresent(meta::setLocalizedName);
        Optional.ofNullable(attributes).ifPresent(meta::setAttributeModifiers);
        // 设置 null 安全的值 / set null safe value.
        meta.setLore(lores);
        meta.setCustomModelData(model);
        meta.setUnbreakable(unbreakable);
        enchantments.forEach((k, v) -> meta.addEnchant(k, v, true));
        flags.forEach(meta::addItemFlags);
        return meta;
    }
}
