package com.alkaidmc.alkaid.serialize.gson;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Map;

public class ItemStackGsonAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    static Gson gson = new Gson();

    @Override
    public ItemStack deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        return ItemStack.deserialize(gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType()));
    }

    @Override
    public JsonElement serialize(ItemStack itemstack, Type type, JsonSerializationContext context) {
        return gson.toJsonTree(itemstack.serialize());
    }
}
