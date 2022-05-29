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

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * <p> zh </p>
 * 指令条件过滤回调接口 <br>
 * 使用接口后将过滤返回 false 的内容 将 true 的结果传递到下一个调用链节点 <br>
 * <p> en </p>
 * Command filter callback interface <br>
 * When the interface is used,
 * the result of the filter will be false will be passed to the next call chain node. <br>
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface AlkaidFilterCallback {
    /**
     * <p> zh </p>
     * 指令条件过滤回调 <br>
     * 使用接口后将过滤返回 false 的内容 将 true 的结果传递到下一个调用链节点 <br>
     * <p> en </p>
     * Command filter callback <br>
     * When the interface is used,
     * the result of the filter will be false will be passed to the next call chain node. <br>
     *
     * @param sender  发送者 / Sender
     * @param command 指令 / Command
     * @param label   别名 / Aliases
     * @param args    参数 / Arguments
     * @return 是否过滤出来 / Filter out
     */
    boolean filter(CommandSender sender, Command command, String label, String[] args);
}
