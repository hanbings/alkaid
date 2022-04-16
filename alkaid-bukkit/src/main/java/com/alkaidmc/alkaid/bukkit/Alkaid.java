package com.alkaidmc.alkaid.bukkit;

import com.alkaidmc.alkaid.bukkit.command.AlkaidCommand;
import com.alkaidmc.alkaid.bukkit.event.AlkaidEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Alkaid extends JavaPlugin {
    static Alkaid alkaid;
    static AlkaidEvent event;
    static AlkaidCommand command;

    public Alkaid() {
        // 静态化实例
        alkaid = this;
        event = new AlkaidEvent(this);
        command = new AlkaidCommand(this);
    }
}
