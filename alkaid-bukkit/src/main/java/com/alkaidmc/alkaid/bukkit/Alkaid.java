package com.alkaidmc.alkaid.bukkit;

import com.alkaidmc.alkaid.bukkit.command.AlkaidCommand;
import com.alkaidmc.alkaid.bukkit.event.AlkaidEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Alkaid {
    static AlkaidEvent event;
    static AlkaidCommand command;

    public Alkaid(JavaPlugin plugin) {
        // 静态化实例
        event = new AlkaidEvent(plugin);
        command = new AlkaidCommand(plugin);
    }
}
