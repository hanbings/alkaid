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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * zh / en <br>
 * 使用本注册器可以快速注册一个 bukkit 命令 <br>
 * use this register to register a bukkit command quickly <br>
 * 需要提供的参数 <br>
 * need args <br>
 *
 * @see Plugin
 * @see PluginCommand
 * @see CommandMap
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class SimpleCommandRegister implements AlkaidCommandRegister {
    final Plugin plugin;
    final PluginCommand instance;
    final CommandMap commands;

    String command;
    List<String> aliases = new ArrayList<>();
    String description;
    String usage;
    String permission;
    CommandExecutor executor = null;
    TabCompleter tab = null;

    @Override
    public void register() {
        // 注入命令参数 / inject command arguments
        instance.setName(command);
        instance.setAliases(aliases);
        instance.setDescription(description);
        instance.setUsage(usage);
        instance.setPermission(permission);
        // 判空处理 / check null
        Optional.ofNullable(executor).ifPresent(instance::setExecutor);
        Optional.ofNullable(tab).ifPresent(instance::setTabCompleter);
        // 向 bukkit 注册指令 / register command to bukkit
        instance.register(commands);
        commands.register("", instance);
    }

    @Override
    public void unregister() {
        instance.unregister(commands);
    }
}
