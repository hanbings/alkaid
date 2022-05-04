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

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class BookInventory {
    // 书信息
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String title = null;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String author = null;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    BookMeta.Generation generation = BookMeta.Generation.ORIGINAL;

    // 书内容
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    List<String> pages = new ArrayList<>();

    public BookInventory write(String content) {
        pages.add(content);
        return this;
    }

    /**
     * 从第 0 页开始数
     *
     * @param page    页数 注意：从第 0 页开始数
     * @param content 内容
     * @return 链式调用对象
     */
    public BookInventory write(int page, String content) {
        if (page + 1 > pages.size()) {
            for (int i = pages.size(); i < page; i++) {
                pages.add("");
            }
        }
        pages.add(page, content);
        return this;
    }

    public ItemStack written() {
        return new ItemStack(Material.WRITTEN_BOOK) {{
            Optional<BookMeta> meta = Optional.ofNullable((BookMeta) getItemMeta());
            meta.ifPresent(m -> {
                // 书信息
                m.setTitle(title);
                m.setAuthor(author);
                m.setGeneration(generation);
                // 写入书内容
                pages.forEach(m::addPage);
                // 写入 meta
                setItemMeta(meta.get());
            });
        }};
    }

    public ItemStack writable() {
        return new ItemStack(Material.WRITABLE_BOOK) {{
            Optional<BookMeta> meta = Optional.ofNullable((BookMeta) getItemMeta());
            meta.ifPresent(m -> {
                // 书信息
                m.setTitle(title);
                m.setAuthor(author);
                m.setGeneration(generation);
                // 写入书内容
                pages.forEach(m::addPage);
                // 写入 meta
                setItemMeta(meta.get());
            });
        }};
    }
}
