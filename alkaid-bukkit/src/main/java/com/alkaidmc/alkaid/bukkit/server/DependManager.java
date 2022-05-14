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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

/**
 * <p> zh </p>
 * 使用该类可以获取插件的依赖插件实例 <br>
 * 与 {@link DependOptional} 的区别是 该类可以获取插件的依赖插件实例 <br>
 * 如果仅需要判断依赖插件是否存在使用 {@link AlkaidServer#depend()} 或 {@link DependOptional} <br>
 * <p> en </p>
 * Use this class to get the dependency plugin instance. <br>
 * The difference between {@link DependOptional} is that
 * this class can get the dependency plugin instance. <br>
 * If only need to judge whether the dependency plugin exists,
 * use {@link AlkaidServer#depend()} or {@link DependOptional} <br>
 *
 * @param <T> 目标插件类型 / Target plugin type
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class DependManager<T extends Plugin> {
    final JavaPlugin plugin;
    final Class<T> type;

    String depend;
    Consumer<T> success;
    Consumer<Exception> fail;

    /**
     * <p> zh </p>
     * 获取依赖插件实例 <br>
     * 如果依赖插件不存在则抛出异常 <br>
     * <p> en </p>
     * Get dependency plugin instance. <br>
     * If the dependency plugin does not exist, throw an exception. <br>
     */
    @SuppressWarnings("unchecked")
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

        success.accept((T) target);
    }
}
