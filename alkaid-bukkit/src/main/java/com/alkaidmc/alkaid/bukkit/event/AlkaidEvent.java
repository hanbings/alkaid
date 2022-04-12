package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.Alkaid;

@SuppressWarnings("unused")
public class AlkaidEvent {
    Alkaid alkaid;

    public AlkaidEvent(Alkaid alkaid) {
        this.alkaid = alkaid;
    }

    public SimpleEventRegister simple() {
        return new SimpleEventRegister(alkaid);
    }

    public CountEventRegister count() {
        return new CountEventRegister(alkaid);
    }
}
