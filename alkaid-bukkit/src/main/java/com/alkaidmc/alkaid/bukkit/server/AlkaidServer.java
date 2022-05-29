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

/**
 * <p> zh </p>
 * 服务端相关工具引导入口 <br>
 * 通过实例化该类可以获得一组服务端相关工具入口方法 <br>
 * <p> en </p>
 * The entry of server related tools. <br>
 * You can get a set of server related tools entry methods by instantiating this class. <br>
 */
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AlkaidServer {
    final Plugin plugin;

    /**
     * @return the dependency manager
     * @see DependManager
     */
    public DependManagerFactory dependent() {
        return new DependManagerFactory();
    }

    /**
     * @return the dependency manager
     * @see DependOptional
     */
    public DependOptional depend() {
        return new DependOptional(plugin);
    }

    /**
     * 包装 {@link DependManager} 以隐藏泛型参数
     * package the {@link DependManager} to hide the generic parameter.
     */
    public class DependManagerFactory {
        /**
         * 获取依赖实例
         * get the dependency instance.
         *
         * @param type 依赖类型 / dependency type
         * @param <T>  需要获取的依赖实例类型 / The type of the dependency instance to get.
         * @return 对应参数类型的依赖实例 / The dependency instance of the corresponding parameter type.
         * @see DependManager
         */
        public <T extends Plugin> DependManager<T> target(Class<T> type) {
            return new DependManager<T>(plugin, type);
        }
    }
}
