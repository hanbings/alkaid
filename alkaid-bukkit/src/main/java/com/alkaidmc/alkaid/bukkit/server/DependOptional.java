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

package com.alkaidmc.alkaid.bukkit.server;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

/**
 * <p> zh </p>
 * 这个类只判断依赖是否加载 不获取依赖的实例 <br>
 * 如需获取实例请使用 {@link AlkaidServer#dependent()} 或 {@link DependManager} <br>
 * <p> en </p>
 * This class only judge whether the dependency is loaded or not. <br>
 * If you need to get the instance of the dependency,
 * please use {@link AlkaidServer#dependent()} or {@link DependManager} <br>
 */
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class DependOptional {
    final JavaPlugin plugin;

    String depend;
    Consumer<Plugin> success;
    Consumer<Exception> fail;

    /**
     * <p> zh </p>
     * 判断依赖是否加载 <br>
     * 如果加载则调用 success 回调 <br>
     * 如果未加载则调用 fail 回调 <br>
     * <p> en </p>
     * Judge whether the dependency is loaded or not. <br>
     * If the dependency is loaded, call the success callback <br>
     * If the dependency is not loaded, call the fail callback <br>
     */
    public void load() {
        if (depend == null) {
            fail.accept(new NullPointerException("Request depend should be not null."));
            return;
        }

        Plugin target = plugin.getServer().getPluginManager().getPlugin(depend);

        if (target == null) {
            fail.accept(new NullPointerException("Request depend not found."));
            return;
        }

        success.accept(target);
    }
}
