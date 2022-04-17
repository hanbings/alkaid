package com.alkaidmc.alkaid.bukkit.config.gson;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.Map;

public class LocationGsonAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {
    static Gson gson = new Gson();

    @Override
    public Location deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        return Location.deserialize(gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType()));
    }

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext context) {
        return gson.toJsonTree(location.serialize());
    }
}
