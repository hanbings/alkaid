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

import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandCallback;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandRegister;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidFilterCallback;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidTabCallback;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * <p> zh </p>
 * 正则匹配命令注册器
 * 当正则条件匹配时 执行对应的指令处理器或 Tab 处理器
 * 使用 {@link #match(String, AlkaidCommandCallback)} 添加指令处理器
 * 使用 {@link #tab(String, AlkaidTabCallback)} 添加 Tab 处理器
 * <p> en </p>
 * Regex command register
 * When the regex condition matches, execute the corresponding command handler or Tab handler.
 * Use {@link #match(String, AlkaidCommandCallback)} to add command handler.
 * Use {@link #tab(String, AlkaidTabCallback)} to add Tab handler.
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class RegexCommandRegister implements AlkaidCommandRegister {
    final Plugin plugin;
    final PluginCommand instance;
    final CommandMap commands;

    String command;
    List<String> aliases = new ArrayList<>();
    String description;
    String usage;
    String permission;

    // 三个处理器 两个精准匹配处理器 一个全匹配处理器
    // Three processors two exact matching processors one full matching processor.
    Consumer<CommandSender> match;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    Map<String, AlkaidCommandCallback> executors = new HashMap<>();
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    Map<String, AlkaidTabCallback> tabs = new HashMap<>();

    // 过滤器 / Filter.
    AlkaidFilterCallback filter;

    @Override
    public void register() {
        instance.setName(command);
        instance.setAliases(aliases);
        instance.setDescription(description);
        instance.setUsage(usage);
        instance.setPermission(permission);
        // 命令处理器 / Command processor.
        instance.setExecutor((sender, command, label, args) -> {
            if (filter != null && !filter.filter(sender, command, label, args)) {
                return false;
            }

            // 如果有全匹配处理器 / If there is a full matching processor.
            Optional.ofNullable(match).ifPresent(e -> e.accept(sender));
            String full = String.join(" ", args);
            // 精准匹配处理器所返回的结果 / The result of the exact matching processor.
            List<Boolean> results = new ArrayList<>();
            // 如果有精准匹配处理器 / If there is an exact matching processor.
            executors.forEach((k, v) -> {
                // 如果精准匹配处理器返回 true / If the exact matching processor returns true.
                if (full.matches(k)) {
                    // 将结果添加到结果集合 / Add the result to the result set.
                    results.add(v.execute(sender, command, label, args));
                }
            });
            // 遍历结果集合 如果有结果为 false 则返回 false 否则返回 true
            // Traverse the result set if there is a result is false then return false otherwise return true.
            return results.stream().anyMatch(r -> !r);
        });
        // 命令 Tab 提示处理器 / Command Tab prompt processor.
        instance.setTabCompleter((sender, command, label, args) -> {
            if (filter != null && filter.filter(sender, command, label, args)) {
                return null;
            }

            String full = String.join(" ", args);
            List<String> results = new ArrayList<>();
            tabs.forEach((k, v) -> {
                if (full.matches(k)) {
                    results.addAll(v.tab(sender, command, label, args));
                }
            });
            return results;
        });
        instance.register(commands);
    }

    @Override
    public void unregister() {
        instance.unregister(commands);
    }

    /**
     * <p> zh </p>
     * 添加匹配处理器 <br>
     * 可多次调用 添加多个正则表达式匹配 <br>
     * <p> en </p>
     * Add matching processor <br>
     * Multiple times call can add multiple regular expressions matching. <br>
     *
     * @param regular  正则表达式 / Regular expression
     * @param callback 匹配处理器 / Matching processor
     * @return 当前实例 / Current instance
     */
    public RegexCommandRegister match(String regular, AlkaidCommandCallback callback) {
        this.executors.put(regular, callback);
        return this;
    }

    /**
     * <p> zh </p>
     * 添加 Tab 提示处理器 <br>
     * 可多次调用 添加多个正则表达式匹配 <br>
     * <p> en </p>
     * Add Tab prompt processor <br>
     * Multiple times call can add multiple regular expressions matching. <br>
     *
     * @param regular  正则表达式 / Regular expression
     * @param callback 匹配处理器 / Matching processor
     * @return 当前实例 / Current instance
     */
    public RegexCommandRegister tab(String regular, AlkaidTabCallback callback) {
        this.tabs.put(regular, callback);
        return this;
    }
}
