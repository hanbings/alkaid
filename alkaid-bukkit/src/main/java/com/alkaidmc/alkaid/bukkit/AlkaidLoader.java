package com.alkaidmc.alkaid.bukkit;

import com.alkaidmc.alkaid.bukkit.type.LoaderType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AlkaidLoader {
    static Map<LoaderType, Consumer<Alkaid>> handlers = new ConcurrentHashMap<>(LoaderType.values().length);
    Alkaid alkaid;

    public AlkaidLoader(Alkaid alkaid) {
        this.alkaid = alkaid;
    }

    public AlkaidLoader loading(Consumer<Alkaid> handler) {
        handlers.put(LoaderType.LOADING, handler);
        return this;
    }

    public AlkaidLoader enable(Consumer<Alkaid> handler) {
        handlers.put(LoaderType.ENABLE, handler);
        return this;
    }

    public AlkaidLoader disable(Consumer<Alkaid> handler) {
        handlers.put(LoaderType.DISABLE, handler);
        return this;
    }

    public void call(LoaderType type) {
        if (handlers.containsKey(type)) {
            handlers.get(type).accept(alkaid);
        }
    }
}
