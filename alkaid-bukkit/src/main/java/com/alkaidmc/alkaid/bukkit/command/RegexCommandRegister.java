package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.Alkaid;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandCallback;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandDescribable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandFilterable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandMatchable;
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
import java.util.Optional;
import java.util.function.Consumer;

public class RegexCommandRegister implements AlkaidCommandRegister, AlkaidCommandDescribable,
        AlkaidCommandMatchable, AlkaidCommandFilterable {
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
    // 过滤器结果
    boolean result = false;

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
        // 命令处理器
        instance.setExecutor((sender, command, label, args) -> {
            // 如果有过滤器
            Optional.ofNullable(filter).ifPresent(f -> {
                // 过滤器结果
                result = f.filter(sender, command, label, args);
            });
            // 如果过滤器结果为true
            if (result) {
                return false;
            }
            // 如果有全匹配处理器
            Optional.ofNullable(executor).ifPresent(e -> {
                e.accept(sender);
            });
            // 数组转为字符串 空格分隔
            String full = String.join(" ", args);
            // 精准匹配处理器所返回的结果
            List<Boolean> results = new ArrayList<>();
            // 如果有精准匹配处理器
            executors.forEach((k, v) -> {
                // 如果精准匹配处理器返回 true
                if (full.matches(k)) {
                    // 将结果添加到结果集合
                    results.add(v.execute(sender, command, label, args));
                }
            });
            // 遍历结果集合 如果有结果为 false 则返回 false 否则返回 true
            return results.stream().anyMatch(r -> !r);
        });
        // 命令 Tab 提示处理器
        instance.setTabCompleter((sender, command, alias, args) -> {
            // 如果有过滤器
            Optional.ofNullable(filter).ifPresent(f -> {
                // 过滤器结果
                result = f.filter(sender, command, alias, args);
            });
            // 如果过滤器结果为true
            if (result) {
                return new ArrayList<>();
            }
            // 数组转为字符串 空格分隔
            String full = String.join(" ", args);
            // 精准匹配处理器所返回的结果
            List<String> results = new ArrayList<>();
            // 如果有精准匹配处理器
            tabs.forEach((k, v) -> {
                // 如果精准匹配处理器返回 true
                if (full.matches(k)) {
                    // 返回结果
                    results.addAll(v.tab(sender, command, alias, args));
                }
            });
            return results;
        });
        instance.register(commands);
        return this;
    }

    @Override
    public RegexCommandRegister unregister() {
        instance.unregister(commands);
        return this;
    }

    @Override
    public RegexCommandRegister filter(AlkaidFilterCallback callback) {
        this.filter = callback;
        return this;
    }

    @Override
    public RegexCommandRegister match(Consumer<CommandSender> callback) {
        this.executor = callback;
        return this;
    }

    @Override
    public RegexCommandRegister match(String regular, AlkaidCommandCallback callback) {
        this.executors.put(regular, callback);
        return this;
    }

    @Override
    public RegexCommandRegister tab(String regular, AlkaidTabCallback callback) {
        this.tabs.put(regular, callback);
        return this;
    }
}
