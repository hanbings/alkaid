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
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class AlkaidEvent {
    final Plugin plugin;
    final SimpleEventFactory simple = new SimpleEventFactory();
    final PredicateEventFactory predicate = new PredicateEventFactory();
    final CountEventFactory count = new CountEventFactory();
    final SectionEventFactory section = new SectionEventFactory();

    public AlkaidEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Register a simple event
     *
     * @return the event factory
     * @see com.alkaidmc.alkaid.bukkit.event.SimpleEventRegister
     */
    public SimpleEventFactory simple() {
        return simple;
    }

    /**
     * Register a predicate event
     *
     * @return the event factory
     * @see com.alkaidmc.alkaid.bukkit.event.PredicateEventRegister
     */
    public PredicateEventFactory predicate() {
        return predicate;
    }

    /**
     * Register a section event
     *
     * @return the event factory
     * @see com.alkaidmc.alkaid.bukkit.event.SectionEventRegister
     */
    public SectionEventFactory section() {
        return section;
    }

    /**
     * Register a count event
     *
     * @return the event factory
     * @see com.alkaidmc.alkaid.bukkit.event.CountEventRegister
     */
    public CountEventFactory count() {
        return count;
    }

    /**
     * Register an event chain.
     *
     * @return the event chain
     * @see com.alkaidmc.alkaid.bukkit.event.ChainEventRegister
     */
    public ChainEventRegister chain() {
        return new ChainEventRegister(plugin);
    }

    public class SimpleEventFactory {
        public <T extends Event> SimpleEventRegister<T> event(Class<T> event) {
            return new SimpleEventRegister<>(plugin, event);
        }
    }

    public class PredicateEventFactory {
        public <T extends Event> PredicateEventRegister<T> event(Class<T> event) {
            return new PredicateEventRegister<>(plugin, event);
        }
    }

    public class CountEventFactory {
        public <T extends Event> CountEventRegister<T> event(Class<T> event) {
            return new CountEventRegister<>(plugin, event);
        }
    }

    public class SectionEventFactory {
        public <T extends Event> SectionEventRegister<T> event(Class<T> event) {
            return new SectionEventRegister<>(plugin, event);
        }
    }
}
