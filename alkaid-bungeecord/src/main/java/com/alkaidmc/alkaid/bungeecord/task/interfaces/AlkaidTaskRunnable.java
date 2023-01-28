/*
 * Copyright 2023 Alkaid
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

package com.alkaidmc.alkaid.bungeecord.task.interfaces;

/**
 * <p> zh </p>
 * 提供一个赋予函数接口的接口 <br>
 * <p> en </p>
 * The interface of providing a function interface. <br>
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface AlkaidTaskRunnable {
    /**
     * <p> zh </p>
     * 任务执行 <br>
     * <p> en </p>
     * task execution. <br>
     */
    void run();
}
