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

import org.bukkit.command.CommandSender;

/**
 * <p> zh </p>
 * 解析树指令回调 <br>
 * 用于执行符合条件的指令 <br>
 * <p> en </p>
 * Parse tree command callback <br>
 * Used to execute the command that meets the conditions. <br>
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface AlkaidParseCommandCallback {
    /**
     * <p> zh </p>
     * 用于执行符合条件的指令 <br>
     * tokens 是 args 的子集 一开始时 tokens 与 args 相同
     * 每一次解析后 tokens 就减少一个被解析成功的元素 <br>
     * deep 是当前解析的深度 也是解析的次数 <br>
     * 当 tokens 为空时表示解析完成 <br>
     * 此时 deep 达到最大值 <br>
     * <p> en </p>
     * Used to execute the command that meets the conditions. <br>
     * tokens is the subset of args. At the beginning, tokens is the same as args. <br>
     * Each time after parsing, tokens will reduce one element that is parsed successfully. <br>
     * deep is the current depth of parsing. It is the number of times of parsing. <br>
     * When tokens is empty and deep reaches the maximum value,
     * it means parsing is completed. <br>
     *
     * @param sender 发送者 / Sender
     * @param args   参数 / Arguments
     * @param tokens 剩余没有被解析的 args 参数 / The remaining args that have not been parsed
     * @param deep   解析深度 / Parse depth
     * @return 指令是否执行成功 / Command execution success
     */
    boolean execute(CommandSender sender, String[] args, String[] tokens, int deep);
}
