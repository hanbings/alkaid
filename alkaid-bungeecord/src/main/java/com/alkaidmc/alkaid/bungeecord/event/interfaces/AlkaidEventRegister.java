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

 package com.alkaidmc.alkaid.bungeecord.event.interfaces;

 /**
  * <p> zh </p>
  * 所有事件注册器的公共接口 <br>
  * 使用 {@link #register()} 方法注册事件到 bungee cord 事件系统 <br>
  * 使用 {@link #unregister()} 方法从 bungee cord 事件系统中注销事件 <br>
  * <p> en </p>
  * The common interface of all event registrar. <br>
  * Use {@link #register()} method to register event to bungee cord event bus. <br>
  * Use {@link #unregister()} method to unregister event from bungee cord event bus. <br>
  */
 @SuppressWarnings("unused")
 public interface AlkaidEventRegister {
     /**
      * 当方法被调用时 才会注册事件到 bungee cord 事件系统
      * When the method is called, the event will be registered to bungee cord event bus.
      */
     void register();
 
     /**
      * 当方法被调用时 才会从 bungee cord 事件系统中注销事件
      * When the method is called, the event will be unregistered from bungee cord event bus.
      * 如有需要 可以重新调用 {@link #register()} 方法注册事件
      * If you need, you can call {@link #register()} method to register event again.
      */
     void unregister();
 }
 