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

    public ChatMessage newline() {
        components.add(new TextComponent("\n"));
        return this;
    }
}
