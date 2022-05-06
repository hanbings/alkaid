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

import com.alkaidmc.alkaid.inventory.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;

@SuppressWarnings("unused")
public class ItemStackBuilder {
    // todo 完成 ItemStack Builder
    // https://github.com/AlkaidMC/alkaid/projects/1#card-81556708

    private ItemStack itemStack;

    public ItemStackBuilder() {
        try {
            Constructor<ItemStack> con = ItemStack.class.getDeclaredConstructor();
            con.setAccessible(true);
            itemStack = con.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public ItemStackBuilder(ItemStack is) {
        this.itemStack = is;
    }

    public ItemStackBuilder setType(Material m) {
        this.itemStack.setType(m);
        return this;
    }

    public ItemStackBuilder setAmount(int m) {
        this.itemStack.setAmount(m);
        return this;
    }

    public ItemStackBuilder setDamage(short d) {
        this.itemStack.setDurability(d);
        return this;
    }

    public ItemStackBuilder setItemMeta(ItemMeta i) {
        this.itemStack.setItemMeta(i);
        return this;
    }

    public ItemStackBuilder setDisplayName(String name) {
        ItemUtil.setDisplayName(this.itemStack, name);
        return this;
    }

    public ItemStackBuilder setSkullOwner(OfflinePlayer p) {
        ItemUtil.setSkullOwner(this.itemStack, p);
        return this;
    }

    public ItemStackBuilder addLore(String... l) {
        ItemUtil.addLore(this.itemStack, l);
        return this;
    }

    public ItemStackBuilder setLore(String... l) {
        ItemUtil.setLore(this.itemStack, l);
        return this;
    }

    public ItemStack create() {
        return this.itemStack;
    }
}
