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

/**
 * <p> zh </p>
 * 事件注册器引导入口 <br>
 * 通过实例化该类可以获得一组事件注册器入口方法 <br>
 * <p> en </p>
 * Event registrar entry point. <br>
 * Get a set of registrar entry points by instantiating this class. <br>
 */
@SuppressWarnings("unused")
public class AlkaidEvent {
    final Plugin plugin;
    final SimpleEventFactory simple = new SimpleEventFactory();
    final PredicateEventFactory predicate = new PredicateEventFactory();
    final CountEventFactory count = new CountEventFactory();
    final SectionEventFactory section = new SectionEventFactory();

    /**
     * constructor
     *
     * @param plugin plugin instance
     */
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

    /**
     * <p> zh </p>
     * 创建一个普通事件工厂 用于包装以隐藏事件泛型输入 <br>
     * 同样可以通过直接实例化 {@link com.alkaidmc.alkaid.bukkit.event.SimpleEventRegister} 来获得该注册器 <br>
     * <p> en </p>
     * Create a normal event factory to wrap the hidden event generic input. <br>
     * You can also get the registrar by
     * directly instantiating {@link com.alkaidmc.alkaid.bukkit.event.SimpleEventRegister} <br>
     */
    public class SimpleEventFactory {
        /**
         * Register a simple event
         *
         * @param event the event
         * @param <T>   the event type
         * @return the event register
         */
        public <T extends Event> SimpleEventRegister<T> event(Class<T> event) {
            return new SimpleEventRegister<>(plugin, event);
        }
    }

    /**
     * <p> zh </p>
     * 创建一个条件事件工厂 用于包装以隐藏事件泛型输入 <br>
     * 同样可以通过直接实例化 {@link com.alkaidmc.alkaid.bukkit.event.PredicateEventRegister} 来获得该注册器 <br>
     * <p> en </p>
     * Create a condition event factory to wrap the hidden event generic input. <br>
     * You can also get the registrar by
     * directly instantiating {@link com.alkaidmc.alkaid.bukkit.event.PredicateEventRegister} <br>
     */
    public class PredicateEventFactory {
        /**
         * Register a simple event
         *
         * @param event the event
         * @param <T>   the event type
         * @return the event register
         */
        public <T extends Event> PredicateEventRegister<T> event(Class<T> event) {
            return new PredicateEventRegister<>(plugin, event);
        }
    }

    /**
     * <p> zh </p>
     * 创建一个计数事件工厂 用于包装以隐藏事件泛型输入 <br>
     * 同样可以通过直接实例化 {@link com.alkaidmc.alkaid.bukkit.event.CountEventRegister} 来获得该注册器 <br>
     * <p> en </p>
     * Create a count event factory to wrap the event input. <br>
     * You can also get the registrar by
     * directly instantiating {@link com.alkaidmc.alkaid.bukkit.event.CountEventRegister}. <br>
     */
    public class CountEventFactory {
        /**
         * Register a simple event
         *
         * @param event the event
         * @param <T>   the event type
         * @return the event register
         */
        public <T extends Event> CountEventRegister<T> event(Class<T> event) {
            return new CountEventRegister<>(plugin, event);
        }
    }

    /**
     * <p> zh </p>
     * 创建一个事件段落工厂 用于包装以隐藏事件泛型输入 <br>
     * 同样可以通过直接实例化 {@link com.alkaidmc.alkaid.bukkit.event.SectionEventRegister} 方法获得注册器 <br>
     * <p> en </p>
     * Create a section factory to wrap the event generic input. <br>
     * You can also get the registrar by
     * directly instantiating {@link com.alkaidmc.alkaid.bukkit.event.SectionEventRegister} <br>
     */
    public class SectionEventFactory {
        /**
         * Register a simple event
         *
         * @param event the event
         * @param <T>   the event type
         * @return the event register
         */
        public <T extends Event> SectionEventRegister<T> event(Class<T> event) {
            return new SectionEventRegister<>(plugin, event);
        }
    }
}
