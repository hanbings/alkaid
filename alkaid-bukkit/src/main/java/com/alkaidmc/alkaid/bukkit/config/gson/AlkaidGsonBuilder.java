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
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * <p> zh </p>
 * Gson 工具类 <br>
 * <p> en </p>
 * Gson tool class.
 */
@SuppressWarnings("unused")
public class AlkaidGsonBuilder {
    static Gson gson;

    /**
     * <p> zh </p>
     * 创建 Gson 对象 <br>
     * 此处创建的 gson 实例将自动包含 alkaid 所携带的用于适配 bukkit 的序列化适配器 <br>
     * 另外创建的实例将被缓存 <br>
     * <p> en </p>
     * Create Gson object. <br>
     * This gson instance will automatically include alkaid's serialization adapter for bukkit. <br>
     * The created instance will be cached. <br>
     *
     * @return Gson 实例 / Gson instance
     */
    public static Gson gson() {
        return Optional.ofNullable(gson).orElseGet(() -> {
            gson = new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .registerTypeAdapter(ItemStack.class, new ItemStackGsonAdapter())
                    .registerTypeAdapter(Player.class, new PlayerGsonAdapter())
                    .registerTypeAdapter(Location.class, new LocationGsonAdapter())
                    .create();
            return gson;
        });
    }
}
