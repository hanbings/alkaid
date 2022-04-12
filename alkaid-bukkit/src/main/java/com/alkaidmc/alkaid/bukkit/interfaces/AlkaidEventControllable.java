package com.alkaidmc.alkaid.bukkit.interfaces;

public interface AlkaidEventControllable {
    void listen();

    void hangup();

    AlkaidEventControllable before(AlkaidEventCallback callback);

    AlkaidEventControllable after(AlkaidEventCallback callback);
}
