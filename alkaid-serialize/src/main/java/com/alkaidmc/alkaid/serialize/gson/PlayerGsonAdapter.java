package com.alkaidmc.alkaid.serialize.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Type;
import java.util.UUID;

public class PlayerGsonAdapter implements JsonSerializer<OfflinePlayer>, JsonDeserializer<OfflinePlayer> {
    @Override
    public OfflinePlayer deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        return Bukkit.getOfflinePlayer(UUID.fromString(json.getAsString()));
    }

    @Override
    public JsonElement serialize(OfflinePlayer player, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(player.getUniqueId().toString());
    }
}
