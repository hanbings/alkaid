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

/**
 * <p> zh </p>
 * ItemStack 序列化适配器 <br>
 * 使用 {@link ItemStack#serialize()} 对象进行序列化 <br>
 * 使用 {@link ItemStack#deserialize(Map)} 对象进行反序列化 <br>
 * <p> en </p>
 * ItemStack serialization adapter. <br>
 * Use {@link ItemStack#serialize()} to serialize. <br>
 * Use {@link ItemStack#deserialize(Map)} to deserialize. <br>
 */
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
