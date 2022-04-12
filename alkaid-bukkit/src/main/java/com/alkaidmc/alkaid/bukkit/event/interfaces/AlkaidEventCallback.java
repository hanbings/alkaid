package com.alkaidmc.alkaid.bukkit.event.interfaces;

import com.alkaidmc.alkaid.bukkit.Alkaid;

@SuppressWarnings("unused")
public interface AlkaidEventCallback {
    void callback(Alkaid alkaid, AlkaidEventRegister register);
}
