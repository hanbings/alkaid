/*
 * Copyright 2022 Alkaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alkaidmc.alkaid.bukkit.event;

import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class AlkaidEvent {
    final JavaPlugin plugin;
    final SimpleEventFactory simple = new SimpleEventFactory();
    final ConditionalEventFactory conditional = new ConditionalEventFactory();
    final CountEventFactory count = new CountEventFactory();
    public AlkaidEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

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
