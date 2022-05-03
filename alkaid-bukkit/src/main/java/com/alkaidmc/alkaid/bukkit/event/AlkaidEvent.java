package com.alkaidmc.alkaid.bukkit.event;

import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class AlkaidEvent {
    final JavaPlugin plugin;

    public AlkaidEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    final SimpleEventFactory simple = new SimpleEventFactory();
    final ConditionalEventFactory conditional = new ConditionalEventFactory();
    final CountEventFactory count = new CountEventFactory();

    public SimpleEventFactory simple() {
        return simple;
    }

    public ConditionalEventFactory conditional() {
        return conditional;
    }

    public CountEventFactory count() {
        return count;
    }

    class SimpleEventFactory {
        public <T extends Event> SimpleEventRegister<T> event(Class<T> event) {
            return new SimpleEventRegister<>(plugin, event);
        }
    }

    class CountEventFactory {
        public <T extends Event> CountEventRegister<T> event(Class<T> event) {
            return new CountEventRegister<>(plugin, event);
        }
    }

    class ConditionalEventFactory {
        public <T extends Event> ConditionalEventRegister<T> event(Class<T> event) {
            return new ConditionalEventRegister<>(plugin, event);
        }
    }
}
