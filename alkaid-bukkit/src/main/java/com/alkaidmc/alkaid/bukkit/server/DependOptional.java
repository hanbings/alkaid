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
import org.bukkit.plugin.java.JavaPlugin;

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
}
