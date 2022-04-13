package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.Alkaid;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandDescribable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandParsable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandParser;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandRegister;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EnhanceCommandRegister implements AlkaidCommandRegister, AlkaidCommandDescribable, AlkaidCommandParsable {
    Alkaid alkaid;
    PluginCommand instance;
    CommandMap commands;
    // 命令相关信息
    String command;
    List<String> aliases = new ArrayList<>();
    String description;
    String usage;
    String permission;
    /*
        执行器 两个 一个全匹配执行器 一个参数匹配执行器
        优先匹配全匹配执行器
     */
    // 全匹配执行器
    Consumer<AlkaidCommandParser> parser = null;
    // 精度匹配
    Map<Consumer<AlkaidCommandParser>, String> parsers = new HashMap<>();

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
    public AlkaidCommandParsable parse(Consumer<AlkaidCommandParser> consumer) {
        parser = consumer;
        return this;
    }

    @Override
    public AlkaidCommandParsable parser(String command, Consumer<AlkaidCommandParser> consumer) {
        parsers.put(consumer, command);
        return this;
    }

    @Override
    public EnhanceCommandRegister register() {
        instance.setName(command);
        instance.setAliases(aliases);
        instance.setDescription(description);
        instance.setUsage(usage);
        instance.setPermission(permission);
        instance.setExecutor((sender, command, label, args) -> {
            if (parser != null) {
                parser.accept(new EnhanceCommandParser(sender, args));
            } else {
                parsers.forEach((consumer, c) -> {
                    if (c.equals(args[1])) {
                        consumer.accept(new EnhanceCommandParser(sender, args));
                    }
                });
            }
            return true;
        });
        instance.register(commands);
        return this;
    }

    @Override
    public void unregister() {
        instance.unregister(commands);
    }
}
