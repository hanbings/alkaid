package com.alkaidmc.alkaid.bukkit;

import com.alkaidmc.alkaid.bukkit.event.ListenerRegister;
import com.alkaidmc.alkaid.bukkit.type.LoaderType;
import org.bukkit.plugin.java.JavaPlugin;

public class Alkaid extends JavaPlugin {
    static Alkaid alkaid;
    static AlkaidLoader loader;
    static ListenerRegister listener;

    public Alkaid() {
        // 静态化实例
        alkaid = this;
        loader = new AlkaidLoader(this);
        listener = new ListenerRegister(this);
        // 获取 AlkaidPlugin 实例

    }

    public static AlkaidLoader loader() {
        return loader;
    }

    public static ListenerRegister event() {
        return listener;
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
