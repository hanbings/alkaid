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

    // todo 流式解析器 ParserCommandRegister
}
