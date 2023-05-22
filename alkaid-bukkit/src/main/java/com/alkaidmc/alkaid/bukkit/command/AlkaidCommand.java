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

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * <p> zh </p>
 * 指令注册器引导入口 <br>
 * 通过实例化该类可以获得一组注册器入口方法 <br>
 * <p> en </p>
 * Command registrar entry point <br>
 * Get a set of registrar entry points by instantiating this class <br>
 */
@AllArgsConstructor
@SuppressWarnings("unused")
public class AlkaidCommand {
    static Constructor<PluginCommand> constructor;
    static CommandMap commandMap;

    static {
        try {
            // 这不得上反射
            constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            // 开锁
            constructor.setAccessible(true);
            field.setAccessible(true);
            // 获取
            commandMap = (CommandMap) field.get(Bukkit.getServer().getPluginManager());
        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    Plugin plugin;

    /**
     * <p> zh </p>
     * 简单指令注册器 <br>
     * 仅包含注册一个指令最基础的参数 <br>
     * <p> en </p>
     * Simple command registrar. <br>
     * Only include one command parameter. <br>
     *
     * @return SimpleCommandRegister 实例 / SimpleCommandRegister instance
     */
    public SimpleCommandRegister simple() {
        try {
            return new SimpleCommandRegister(
                    plugin,
                    constructor.newInstance("", plugin),
                    commandMap
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * <p> zh </p>
     * 正则匹配指令注册器 <br>
     * 使用正则匹配一条完整指令的方式进行解析 <br>
     * 推荐用于简单指令内容但是需要更多的参数的情况 <br>
     * <p> en </p>
     * Regex matching command registrar. <br>
     * Use regex matching a complete command to parse. <br>
     * Recommended for simple command content but need more parameters. <br>
     *
     * @return RegexCommandRegister 实例 / RegexCommandRegister instance
     */
    public RegexCommandRegister regex() {
        try {
            return new RegexCommandRegister(
                    plugin,
                    constructor.newInstance("", plugin),
                    commandMap
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * <p> zh </p>
     * 解析树指令注册器 <br>
     * 该解析器将指令拆分为单独的部分 逐个匹配 <br>
     * 推荐用于参数类型较为复杂的情况 <br>
     * <p> en </p>
     * Parser tree command registrar. <br>
     * The parser will split the command into separate parts and match one by one. <br>
     * Recommended for complex parameter types. <br>
     *
     * @return ParseCommandRegister 实例 / ParseCommandRegister instance
     */
    public ParseCommandRegister parse() {
        try {
            return new ParseCommandRegister(
                    plugin,
                    constructor.newInstance("", plugin),
                    commandMap
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
