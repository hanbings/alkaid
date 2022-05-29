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

package com.alkaidmc.alkaid.bukkit.event.interfaces;

import org.bukkit.plugin.Plugin;

/**
 * <p> zh </p>
 * 事件回调接口 <br>
 * 部分注册器实现该接口 <br>
 * <p> en </p>
 * Event callback interface. <br>
 * Some registrar implement this interface. <br>
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface AlkaidEventCallback {
    /**
     * <p> zh </p>
     * 回调返回插件实例和注册器实例 <br>
     * 允许开发者进行挂起 注销等操作 <br>
     * <p> en </p>
     * Callback return plugin instance and registrar instance. <br>
     * Allow developers to do some suspend, unregister, etc. operations. <br>
     *
     * @param plugin   注册事件所用的 Plugin 实例 / The plugin instance used to register the event.
     * @param register 注册器实例 / The registrar instance.
     */
    void callback(Plugin plugin, AlkaidEventRegister register);
}
