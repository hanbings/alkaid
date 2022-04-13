package com.alkaidmc.alkaid.bukkit;

import com.alkaidmc.alkaid.bukkit.command.AlkaidCommand;
import com.alkaidmc.alkaid.bukkit.event.AlkaidEvent;
import com.alkaidmc.alkaid.bukkit.type.LoaderType;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Alkaid extends JavaPlugin {
    static Alkaid alkaid;
    static AlkaidLoader loader;
    static AlkaidEvent event;
    static AlkaidCommand command;

    public Alkaid() {
        // 静态化实例
        alkaid = this;
        loader = new AlkaidLoader(this);
        event = new AlkaidEvent(this);
        command = new AlkaidCommand(this);
        // 获取 AlkaidPlugin 实例

    }

    public AlkaidLoader loader() {
        return loader;
    }

    public AlkaidEvent event() {
        return event;
    }

    @Override
    public void onLoad() {
        loader.call(LoaderType.LOADING);
    }

    @Override
    public void onEnable() {
        loader.call(LoaderType.ENABLE);
    }

    @Override
    public void onDisable() {
        loader.call(LoaderType.DISABLE);
    }
}
