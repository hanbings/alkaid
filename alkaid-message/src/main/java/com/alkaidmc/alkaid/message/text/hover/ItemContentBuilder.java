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

package com.alkaidmc.alkaid.message.text.hover;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.inventory.ItemStack;

/**
 * @author Milkory
 */
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class ItemContentBuilder implements ContentBuilder<Item> {
    private String id;
    private int count = -1;
    private ItemTag tag;

    public ItemContentBuilder tag(String nbt) {
        this.tag = ItemTag.ofNbt(nbt);
        return this;
    }

    public ItemContentBuilder item(ItemStack item) {
        this.id = item.getType().getKey().toString();
        this.count = item.getAmount();
        // todo get item nbt
        return this;
    }

    @Override
    public Item buildContent() {
        return new Item(id, count, tag);
    }
}
