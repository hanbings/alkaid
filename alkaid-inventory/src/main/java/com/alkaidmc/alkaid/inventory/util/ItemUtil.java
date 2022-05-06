package com.alkaidmc.alkaid.inventory.util;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemUtil {
    public static void setDisplayName(ItemStack is, String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
    }

    public static void addLore(ItemStack is, String... lore) {
        setLore(is, toArray(append(is.getItemMeta().getLore(), lore), String.class));
    }

    public static void setLore(ItemStack is, String... lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(List.of(lore));
        is.setItemMeta(im);
    }

    public static boolean setSkullOwner(ItemStack s, OfflinePlayer p) {
        ItemMeta im = s.getItemMeta();
        if (!(im instanceof SkullMeta))
            return false;
        SkullMeta sm = (SkullMeta) im;
        sm.setOwningPlayer(p);
        s.setItemMeta(sm);
        return true;
    }

    public static String getDisplayName(ItemStack is) {
        return is.getItemMeta().getDisplayName();
    }

    public static <T> T[] toArray(List<T> l, Class<T> clazz) {
        T[] ret = (T[]) java.lang.reflect.Array.newInstance(clazz, l.size());
        for (int i = 0; i < l.size(); i++)
            ret[i] = l.get(i);
        return ret;
    }

    public static <T> List<T> append(List<T> l, T... o) {
        List<T> temp = new ArrayList<>(l);
        Collections.addAll(temp, o);
        return temp;
    }
}