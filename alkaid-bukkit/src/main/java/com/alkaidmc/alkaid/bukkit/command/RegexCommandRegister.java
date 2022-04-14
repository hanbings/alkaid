package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.Alkaid;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandCallback;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandDescribable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandRegister;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidFilterCallback;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidTabCallback;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RegexCommandRegister implements AlkaidCommandRegister, AlkaidCommandDescribable {
    Alkaid alkaid;
    PluginCommand instance;
    CommandMap commands;
    // 命令相关信息
    String command;
    List<String> aliases = new ArrayList<>();
    String description;
    String usage;
    String permission;
    // 三个处理器 两个精准匹配处理器 一个全匹配处理器
    Consumer<CommandSender> executor;
    Map<String, AlkaidCommandCallback> executors = new HashMap<>();
    Map<String, AlkaidTabCallback> tabs = new HashMap<>();
    // 过滤器
    AlkaidFilterCallback filter;

    public RegexCommandRegister(Alkaid alkaid, PluginCommand instance, CommandMap commands) {
        this.alkaid = alkaid;
        this.instance = instance;
        this.commands = commands;
    }

    @Override
    public RegexCommandRegister command(String command) {
        this.command = command;
        return this;
    }

    @Override
    public RegexCommandRegister description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public RegexCommandRegister usage(String usage) {
        this.usage = usage;
        return this;
    }

    @Override
    public RegexCommandRegister permission(String permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public RegexCommandRegister alias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    @Override
    public RegexCommandRegister aliases(String... aliases) {
        // 将数组转换为list
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    @Override
    public RegexCommandRegister register() {
        instance.setName(command);
        instance.setAliases(aliases);
        instance.setDescription(description);
        instance.setUsage(usage);
        instance.setPermission(permission);

        instance.register(commands);
        return this;
    }

    @Override
    public RegexCommandRegister unregister() {
        instance.unregister(commands);
        return this;
    }
}
