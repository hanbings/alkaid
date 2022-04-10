package com.alkaidmc.alkaid.bukkit;

import com.alkaidmc.alkaid.bukkit.type.LoaderType;
import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AlkaidLoader extends JavaPlugin {
    static AlkaidLoader alkaid;
    static Map<LoaderType, Consumer<JavaPlugin>> handlers = new ConcurrentHashMap<>(LoaderType.values().length);

    public AlkaidLoader() {
        // 静态化实例
        alkaid = this;
        // 获取 AlkaidPlugin 实例
        
    }

    @Override
    public void onLoad() {
        if (handlers.containsKey(LoaderType.LOADING)) {
            handlers.get(LoaderType.LOADING).accept(this);
        }
    }

    @Override
    public void onEnable() {
        if (handlers.containsKey(LoaderType.ENABLE)) {
            handlers.get(LoaderType.ENABLE).accept(this);
        }
    }

    @Override
    public void onDisable() {
        if (handlers.containsKey(LoaderType.DISABLE)) {
            handlers.get(LoaderType.DISABLE).accept(this);
        }
        if (handlers.containsKey(LoaderType.DONE)) {
            handlers.get(LoaderType.DONE).accept(this);
        }
    }

    public AlkaidLoader loading(Consumer<JavaPlugin> handler) {
        handlers.put(LoaderType.LOADING, handler);
        return this;
    }

    public AlkaidLoader enable(Consumer<JavaPlugin> handler) {
        handlers.put(LoaderType.ENABLE, handler);
        return this;
    }

    public AlkaidLoader disable(Consumer<JavaPlugin> handler) {
        handlers.put(LoaderType.DISABLE, handler);
        return this;
    }

    public void done(Consumer<JavaPlugin> handler) {
        handlers.put(LoaderType.DONE, handler);
    }
}
