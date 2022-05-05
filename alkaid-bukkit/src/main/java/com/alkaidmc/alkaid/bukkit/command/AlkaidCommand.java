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

import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unused")
public class AlkaidCommand {
    JavaPlugin plugin;
    Constructor<PluginCommand> constructor;
    CommandMap commandMap;

    public AlkaidCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        try {
            // 这不得上反射
            constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            // 开锁
            constructor.setAccessible(true);
            field.setAccessible(true);
        } catch (NoSuchMethodException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

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
