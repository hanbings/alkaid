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

package com.alkaidmc.alkaid.bukkit.task.interfaces;

/**
 * <p> zh </p>
 * 所有任务注册器的公共接口 <br>
 * 使用 {@link #register()} 方法注册任务到 bukkit 任务调度器 <br>
 * 使用 {@link #unregister()} 方法从 bukkit 任务调度器中取消任务 <br>
 * <p> en </p>
 * The common interface of all task registrar. <br>
 * Use {@link #register()} method to register task to bukkit task scheduler. <br>
 * Use {@link #unregister()} method to cancel task from bukkit task scheduler. <br>
 */
@SuppressWarnings("unused")
public interface AlkaidTaskRegister {
    /**
     * 当方法被调用时 才会注册任务到 bukkit 任务调度器
     * When the method is called, the task will be registered to bukkit task scheduler.
     */
    void register();

    /**
     * 当方法被调用时 才会从 bukkit 任务调度器中取消任务
     * When the method is called, the task will be unregistered from bukkit task scheduler.
     * 如有需要 可以重新调用 {@link #register()} 方法注册任务
     * If you need, you can call {@link #register()} method to register task again.
     */
    void unregister();
}
