package com.alkaidmc.alkaid.bukkit.config.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AlkaidGsonBuilder {
    public static Gson create() {
        return new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(ItemStack.class, new ItemStackGsonAdapter())
                .registerTypeAdapter(Player.class, new PlayerGsonAdapter())
                .registerTypeAdapter(Location.class, new LocationGsonAdapter())
                .create();
    }
}
