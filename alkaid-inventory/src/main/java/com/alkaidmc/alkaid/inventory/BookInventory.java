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

    public BookInventory write(int page, String content) {
        if (page > pages.size()) {
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
