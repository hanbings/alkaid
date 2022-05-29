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

package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidParseCommandCallback;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidParseTabCallback;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * <p> zh </p>
 * 此类型本质为处理器容器 <br>
 * 使用 {@link #argument(String, Consumer)} 添加匹配节点 <br>
 * <p> en </p>
 * This class is a container for handler. <br>
 * Use {@link #argument(String, Consumer)} to add matching node. <br>
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class ParseCommandParser {
    final CommandSender sender;
    final String[] args;
    final String[] tokens;
    final int deep;

    @Getter(AccessLevel.NONE)
    AlkaidParseCommandCallback execute;
    @Getter(AccessLevel.NONE)
    AlkaidParseTabCallback tab;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    ParseCommandParser next;

    /**
     * <p> zh </p>
     * 添加匹配节点 <br>
     * 解析到匹配节点时 可选择性执行 {@link #execute()} 或 {@link #tab()} 执行解析器 <br>
     * <p> en </p>
     * Add matching node <br>
     * When the parser reaches the matching node,
     * the parser can optionally execute {@link #execute()} or {@link #tab()} to execute the parser. <br>
     *
     * @param command 单个参数 / single argument
     * @param parser  处理器 / executor
     */
    public void argument(String command, Consumer<ParseCommandParser> parser) {
        if (tokens.length <= 0) {
            return;
        }

        if (tokens[0].equalsIgnoreCase(command)) {
            next = new ParseCommandParser(
                    this.sender,
                    this.args,
                    // token 减少一层 / token delete one level
                    Arrays.copyOfRange(this.tokens, 1, this.tokens.length),
                    // deep 增加一层 / deep add one level
                    this.deep + 1
            );
            parser.accept(next);
        }
    }

    /**
     * <p> zh </p>
     * 执行解析器 <br>
     * 当解析到匹配节点时 可选择性执行此方法 <br>
     * <p> en </p>
     * Execute the parser <br>
     * When the parser reaches the matching node,
     * the parser can optionally execute this method. <br>
     *
     * @return 是否执行成功 / is executed successfully
     */
    public boolean execute() {
        return next != null
                ? next.execute()
                : Optional.of(execute.execute(sender, args, tokens, deep)).orElse(false);
    }

    /**
     * <p> zh </p>
     * 返回 Tab 列表 <br>
     * 当解析到匹配节点时 可选择性执行此方法 <br>
     * <p> en </p>
     * Return Tab list <br>
     * When the parser reaches the matching node,
     * the parser can optionally execute this method. <br>
     *
     * @return Tab 列表 / Tab list
     */
    public List<String> tab() {
        return next != null
                ? next.tab()
                : Optional.of(tab.tab(sender, args, tokens, deep)).orElse(null);
    }
}
