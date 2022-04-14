package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.Alkaid;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unused")
public class AlkaidCommand {
    Alkaid alkaid;
    Constructor<PluginCommand> constructor;
    CommandMap commandMap;

    public AlkaidCommand(Alkaid alkaid) {
        this.alkaid = alkaid;
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
                    alkaid,
                    constructor.newInstance("", alkaid),
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
                    alkaid,
                    constructor.newInstance("", alkaid),
                    commandMap
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
