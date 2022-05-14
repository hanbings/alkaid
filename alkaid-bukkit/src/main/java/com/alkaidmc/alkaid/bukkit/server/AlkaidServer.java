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

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AlkaidServer {
    final JavaPlugin plugin;

    public DependManagerFactory dependent() {
        return new DependManagerFactory();
    }

    public DependOptional depend() {
        return new DependOptional(plugin);
    }

    public class DependManagerFactory {
        public <T extends Plugin> DependManager<T> target(Class<T> type) {
            return new DependManager<T>(plugin, type);
        }
    }
}
