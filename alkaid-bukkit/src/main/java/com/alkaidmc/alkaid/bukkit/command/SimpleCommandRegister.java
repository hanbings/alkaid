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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SimpleCommandRegister implements AlkaidCommandRegister {
    final JavaPlugin plugin;
    final PluginCommand instance;
    final CommandMap commands;

    // 命令相关信息
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String command;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    List<String> aliases = new ArrayList<>();
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String description;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String usage;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String permission;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    CommandExecutor executor = null;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    TabCompleter tab = null;


    @Override
    public void register() {
        instance.setName(command);
        instance.setAliases(aliases);
        instance.setDescription(description);
        instance.setUsage(usage);
        instance.setPermission(permission);
        Optional.ofNullable(executor).ifPresent(instance::setExecutor);
        Optional.ofNullable(tab).ifPresent(instance::setTabCompleter);
        instance.register(commands);
    }

    @Override
    public void unregister() {
        instance.unregister(commands);
    }
}
