package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandDescribable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandExecutable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandRegister;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SimpleCommandRegister implements AlkaidCommandRegister, AlkaidCommandDescribable, AlkaidCommandExecutable {
    JavaPlugin plugin;
    PluginCommand instance;
    CommandMap commands;
    // 命令相关信息
    String command;
    List<String> aliases = new ArrayList<>();
    String description;
    String usage;
    String permission;
    CommandExecutor executor = null;
    TabCompleter tab = null;

    public SimpleCommandRegister(JavaPlugin plugin, PluginCommand instance, CommandMap commands) {
        this.plugin = plugin;
        this.instance = instance;
        this.commands = commands;
    }

    @Override
    public SimpleCommandRegister command(String command) {
        this.command = command;
        return this;
    }

    @Override
    public SimpleCommandRegister description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public SimpleCommandRegister usage(String usage) {
        this.usage = usage;
        return this;
    }

    @Override
    public SimpleCommandRegister permission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public SimpleCommandRegister alias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    @Override
    public SimpleCommandRegister aliases(String... aliases) {
        // 将数组转换为list
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    @Override
    public SimpleCommandRegister execute(CommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public SimpleCommandRegister tab(TabCompleter completer) {
        this.tab = completer;
        return this;
    }

    @Override
    public SimpleCommandRegister register() {
        instance.setName(command);
        instance.setAliases(aliases);
        instance.setDescription(description);
        instance.setUsage(usage);
        instance.setPermission(permission);
        Optional.ofNullable(executor).ifPresent(instance::setExecutor);
        Optional.ofNullable(tab).ifPresent(instance::setTabCompleter);
        instance.register(commands);
        return this;
    }

    @Override
    public SimpleCommandRegister unregister() {
        instance.unregister(commands);
        return this;
    }
}
