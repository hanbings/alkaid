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

package com.alkaidmc.alkaid.message;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ChatMessage {
    List<BaseComponent> components = new ArrayList<>();

    public ChatMessage text(String text) {
        components.add(new TextComponent(text));
        return this;
    }

    public ChatMessage text(String text, ChatColor color) {
        TextComponent component = new TextComponent(text);
        component.setColor(color);
        components.add(component);
        return this;
    }

    // todo 悬空字、文字属性等实现
    // https://github.com/AlkaidMC/alkaid/projects/1#card-81556944

    public ChatMessage newline() {
        components.add(new TextComponent("\n"));
        return this;
    }
}
