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

package com.alkaidmc.alkaid.bukkit.task;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

/**
 * <p> zh </p>
 * 任务相关封装引导入口 <br>
 * 通过实例化该类可以获得一组任务封装入口方法 <br>
 * <p> en </p>
 * The entry point of task related wrapper. <br>
 * You can get a set of task wrapper entry methods by instantiating this class. <br>
 */
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AlkaidTask {
    final Plugin plugin;

    /**
     * @return the simple task register
     * @see SimpleTaskRegister
     */
    public SimpleTaskRegister simple() {
        return new SimpleTaskRegister(plugin);
    }
}
