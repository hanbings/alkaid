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

package com.alkaidmc.alkaid.bukkit.config.gson;

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

/**
 * <p> zh </p>
 * Player 序列化适配器 <br>
 * 使用 {@link OfflinePlayer#getUniqueId()} 对象进行序列化 <br>
 * 使用 {@link Bukkit#getPlayer(UUID)} 对象进行反序列化 <br>
 * <p> en </p>
 * Player serialization adapter. <br>
 * Use {@link OfflinePlayer#getUniqueId()} to serialize. <br>
 * Use {@link Bukkit#getPlayer(UUID)} to deserialize. <br>
 */
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
