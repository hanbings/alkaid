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
 * 指令回调接口 <br>
 * 当指令被触发时会调用该接口 <br>
 * <p> en </p>
 * Command callback interface <br>
 * When the command is triggered, it will call this interface <br>
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface AlkaidCommandCallback {
    /**
     * <p> zh </p>
     * 指令回调 <br>
     * 当指令被触发时会调用该方法 <br>
     * 唯一抽象方法以确保 {@link FunctionalInterface} 的实现 <br>
     * <p> en </p>
     * Command callback <br>
     * When the command is triggered, it will call this method. <br>
     * The only abstract method to ensure {@link FunctionalInterface} implementation. <br>
     *
     * @param sender  发送者 / Sender
     * @param command 指令 / Command
     * @param aliases 别名 / Aliases
     * @param args    参数 / Arguments
     * @return 指令是否执行成功 / Command execution success
     */
    boolean execute(CommandSender sender, Command command, String aliases, String[] args);
}
