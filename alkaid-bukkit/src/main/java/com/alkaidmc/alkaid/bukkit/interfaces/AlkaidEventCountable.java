package com.alkaidmc.alkaid.bukkit.interfaces;

public interface AlkaidEventCountable {
    AlkaidEventCountable restart();

    AlkaidEventCountable count(int count);

    AlkaidEventCountable before(AlkaidEventCallback callback);

    AlkaidEventCountable after(AlkaidEventCallback callback);
}
