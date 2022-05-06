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

import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandRegister;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidFilterCallback;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class ParseCommandRegister implements AlkaidCommandRegister {
    final JavaPlugin plugin;
    final PluginCommand instance;
    final CommandMap commands;

    // 命令相关信息 / Command information
    String command;
    List<String> aliases = new ArrayList<>();
    String description;
    String usage;
    String permission;

    // 解析器 / Parser
    Consumer<ParseCommandParser> parsing;

    // 过滤器 / Filter.
    AlkaidFilterCallback filter;

    // 过滤器结果 / Filter result.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    boolean result = false;

    // 初始解析器 / Initial parser.
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    ParseCommandParser parser;

    @Override
    public void register() {
        instance.setName(command);
        instance.setAliases(aliases);
        instance.setDescription(description);
        instance.setUsage(usage);
        instance.setPermission(permission);
        // 命令处理器 / Command processor.
        instance.setExecutor((sender, command, label, args) -> {
            Optional.ofNullable(filter).ifPresent(f -> result = f.filter(sender, command, label, args));
            if (result) {
                return false;
            }

            parser = new ParseCommandParser(
                    sender,
                    args,
                    // token 减少一层 / token delete one level
                    Arrays.copyOfRange(args, 1, args.length),
                    // deep 增加一层 / deep add one level
                    1
            );
            // 处理数据注入完成后的回调 / Callback after data injection.
            parsing.accept(parser);
            // 因为已经在子解析器完成了数据处理 故直接返回
            // Because data processing has been completed in the child parser, so directly return.
            return parser.execute();
        });
        // 命令 Tab 提示处理器 / Command Tab prompt processor.
        instance.setTabCompleter((sender, command, label, args) -> {
            Optional.ofNullable(filter).ifPresent(f -> result = f.filter(sender, command, label, args));
            if (result) {
                return null;
            }

            parsing.accept(parser);
            return parser.tab();
        });
        instance.register(commands);
    }

    @Override
    public void unregister() {
        instance.unregister(commands);
    }
}
