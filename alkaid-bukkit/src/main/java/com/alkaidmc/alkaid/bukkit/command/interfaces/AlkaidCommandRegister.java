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

package com.alkaidmc.alkaid.bukkit.command.interfaces;

/**
 * <p> zh </p>
 * 指令注册方法 <br>
 * 调用 {@link AlkaidCommandRegister#register()} 向 bukkit 注册指令 <br>
 * 调用 {@link AlkaidCommandRegister#unregister()} 从 bukkit 注销指令 <br>
 * <p> en </p>
 * Command register method <br>
 * Call {@link AlkaidCommandRegister#register()} to register the command to bukkit. <br>
 * Call {@link AlkaidCommandRegister#unregister()} to unregister the command from bukkit. <br>
 */
@SuppressWarnings("unused")
public interface AlkaidCommandRegister {

    /**
     * <p> zh </p>
     * 注册指令 <br>
     * 向 bukkit 注册指令 <br>
     * 只有调用该方法时才会将参数注入 bukkit <br>
     * <p> en </p>
     * Register command <br>
     * Register the command to bukkit. <br>
     * Only when calling this method, the parameters will be injected to bukkit. <br>
     */
    void register();

    /**
     * <p> zh </p>
     * 从 bukkit 注销指令 <br>
     * 调用该方法从 bukkit 的指令系统注销指令 <br>
     * 如果有需要可以再次调用 {@link AlkaidCommandRegister#register()} 注册指令 <br>
     * <p> en </p>
     * Unregister command <br>
     * Unregister the command from bukkit. <br>
     * If you need, you can call {@link AlkaidCommandRegister#register()} to register the command again. <br>
     */
    void unregister();
}
