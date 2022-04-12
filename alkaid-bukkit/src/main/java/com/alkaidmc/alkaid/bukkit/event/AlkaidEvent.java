package com.alkaidmc.alkaid.bukkit.event;

import com.alkaidmc.alkaid.bukkit.Alkaid;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AlkaidEvent {
    Alkaid alkaid;

    public AlkaidEvent(Alkaid alkaid) {
        this.alkaid = alkaid;
    }

    public void register(Consumer<AlkaidEvent> register) {
        register.accept(this);
    }

    public SimpleEventRegister simple() {
        return new SimpleEventRegister(alkaid);
    }

    public CountEventRegister count() {
        return new CountEventRegister(alkaid);
    }
}
