package com.alkaidmc.alkaid.bukkit.event.interfaces;

@SuppressWarnings("unused")
public interface AlkaidEventControllable {
    void listen();

    void hangup();

    AlkaidEventControllable before(AlkaidEventCallback callback);

    AlkaidEventControllable after(AlkaidEventCallback callback);
}
