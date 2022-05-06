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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class ItemStackBuilder {
    // todo 完成 ItemStack Builder
    // https://github.com/AlkaidMC/alkaid/projects/1#card-81556708

    static final ItemStack DEFAULT_ITEM = new ItemStack(Material.STONE);

    int amount;

    Consumer<ItemMetaBuilder> meta;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    ItemStack item = DEFAULT_ITEM.clone();

    public ItemStackBuilder of(ItemStack item) {
        this.item = item;
        // 解析 item stack 到 builder / parser item stack to builder
        this.amount = item.getAmount();
        this.size = item.getMaxStackSize();
        return this;
    }

    public ItemStackBuilder of(Material material) {
        this.item = new ItemStack(material);
        return this;
    }

    public ItemStack item() {
        item.setAmount(amount);
        return item.clone();
    }
}
