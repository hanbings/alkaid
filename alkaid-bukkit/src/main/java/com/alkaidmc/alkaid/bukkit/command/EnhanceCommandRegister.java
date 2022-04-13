package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.Alkaid;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandDescribable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandExecutable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandRegister;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnhanceCommandRegister implements AlkaidCommandRegister, AlkaidCommandDescribable, AlkaidCommandExecutable {
    Alkaid alkaid;
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

    public EnhanceCommandRegister(Alkaid alkaid, PluginCommand instance, CommandMap commands) {
        this.alkaid = alkaid;
        this.instance = instance;
        this.commands = commands;
    }

    @Override
    public EnhanceCommandRegister command(String command) {
        this.command = command;
        return this;
    }

    @Override
    public EnhanceCommandRegister description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public EnhanceCommandRegister usage(String usage) {
        this.usage = usage;
        return this;
    }

    @Override
    public EnhanceCommandRegister permission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public EnhanceCommandRegister alias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    @Override
    public EnhanceCommandRegister aliases(String... aliases) {
        // 将数组转换为list
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    @Override
    public EnhanceCommandRegister execute(CommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public EnhanceCommandRegister tab(TabCompleter completer) {
        this.tab = completer;
        return this;
    }

    @Override
    public EnhanceCommandRegister register() {
        instance.setName(command);
        instance.setAliases(aliases);
        instance.setDescription(description);
        instance.setUsage(usage);
        instance.setPermission(permission);
        instance.setExecutor(executor);
        instance.setTabCompleter(tab);
        instance.register(commands);
        return this;
    }

    @Override
    public void unregister() {
        instance.unregister(commands);
    }
}
