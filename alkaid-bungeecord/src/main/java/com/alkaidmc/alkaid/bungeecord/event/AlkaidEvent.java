/*
 * Copyright 2023 Alkaid
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

package com.alkaidmc.alkaid.bungeecord.event;

import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventBus;
import net.md_5.bungee.event.EventHandlerMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class AlkaidEvent {
    final Plugin plugin;
    final SimpleEventFactory simple = new SimpleEventFactory();

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
     * @see com.alkaidmc.alkaid.bungeecord.event.SimpleEventRegister
     */
    public SimpleEventFactory simple() {
        return simple;
    }

    /**
     * <p> zh </p>
     * 创建一个普通事件工厂 用于包装以隐藏事件泛型输入 <br>
     * 同样可以通过直接实例化 {@link com.alkaidmc.alkaid.bungeecord.event.SimpleEventRegister} 来获得该注册器 <br>
     * <p> en </p>
     * Create a normal event factory to wrap the hidden event generic input. <br>
     * You can also get the registrar by
     * directly instantiating {@link com.alkaidmc.alkaid.bungeecord.event.SimpleEventRegister} <br>
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

    public static <T extends Event> void registerEvent(
            Plugin plugin, Class<T> event, byte priority, Consumer<T> listener)
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        registerEvent(plugin, event, priority, AlkaidEvent.class, listener);
    }

    /**
     * register a bungee cord event.
     *
     * @param plugin   bungee cord plugin instance
     * @param event    event
     * @param priority event priority, use byte from EventPriority
     * @param flag     a flag class
     * @param listener event executor
     * @param <T>      event type
     * @throws NoSuchFieldException   reflect exception
     * @throws IllegalAccessException reflect exception
     * @throws NoSuchMethodException  reflect exception
     */
    public static <T extends Event> void registerEvent(
            Plugin plugin, Class<T> event, byte priority, Class<?> flag, Consumer<T> listener)
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        // get plugin manager
        PluginManager manager = plugin.getProxy().getPluginManager();

        // get eventBus from plugin manager object
        Field eventbusField = manager.getClass().getDeclaredField("eventBus");
        eventbusField.setAccessible(true);
        EventBus eventbus = (EventBus) eventbusField.get(manager);

        // get byListenerAndPriority from eventbus object
        Field byListenerAndPriorityField = eventbus.getClass().getDeclaredField("byListenerAndPriority");
        byListenerAndPriorityField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, Map<Byte, Map<Object, Method[]>>> byListenerAndPriority =
                (Map<Class<?>, Map<Byte, Map<Object, Method[]>>>) byListenerAndPriorityField.get(eventbus);

        // get byEventBaked from eventbus object
        Field byEventBakedField = eventbus.getClass().getDeclaredField("byEventBaked");
        byEventBakedField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Class<?>, EventHandlerMethod[]> byEventBaked =
                (Map<Class<?>, EventHandlerMethod[]>) byEventBakedField.get(eventbus);

        // get lock from eventbus object
        Field lockField = eventbus.getClass().getDeclaredField("lock");
        lockField.setAccessible(true);
        Lock lock = (Lock) lockField.get(eventbus);

        // eventbus post event should invoke a method
        // this method from Consumer#apply
        Method method = listener.getClass().getDeclaredMethod("accept", Object.class);

        // lock
        lock.lock();

        // tag priority.
        Map<Byte, Map<Object, Method[]>> prioritiesMap =
                byListenerAndPriority.computeIfAbsent(flag, k -> new HashMap<>());
        Map<Object, Method[]> currentPriorityMap =
                prioritiesMap.computeIfAbsent(priority, k -> new HashMap<>());
        // I don't understand this sentence :(
        currentPriorityMap.put(listener, new Method[]{method});

        // copy from EventBus#bakeHandler
        Map<Byte, Map<Object, Method[]>> handlersByPriority = byListenerAndPriority.get(flag);
        if (handlersByPriority != null) {
            List<EventHandlerMethod> handlersList = new ArrayList<>(handlersByPriority.size() * 2);
            byte value = Byte.MIN_VALUE;
            do {
                Map<Object, Method[]> handlersByListener = handlersByPriority.get(value);
                if (handlersByListener != null) {
                    for (Map.Entry<Object, Method[]> listenerHandlers : handlersByListener.entrySet()) {
                        for (Method m : listenerHandlers.getValue()) {
                            EventHandlerMethod ehm = new EventHandlerMethod(listenerHandlers.getKey(), m);
                            handlersList.add(ehm);
                        }
                    }
                }
            } while (value++ < Byte.MAX_VALUE);
            byEventBaked.put(flag, handlersList.toArray(new EventHandlerMethod[0]));
        } else {
            byEventBaked.remove(flag);
        }

        // unlock
        lock.unlock();
    }
}
