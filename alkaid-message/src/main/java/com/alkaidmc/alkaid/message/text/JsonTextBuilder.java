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

package com.alkaidmc.alkaid.message.text;

import com.alkaidmc.alkaid.message.text.hover.ContentBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.ScoreComponent;
import net.md_5.bungee.api.chat.SelectorComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.md_5.bungee.api.ChatColor.AQUA;
import static net.md_5.bungee.api.ChatColor.BLACK;
import static net.md_5.bungee.api.ChatColor.BLUE;
import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_BLUE;
import static net.md_5.bungee.api.ChatColor.DARK_GRAY;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;
import static net.md_5.bungee.api.ChatColor.DARK_PURPLE;
import static net.md_5.bungee.api.ChatColor.DARK_RED;
import static net.md_5.bungee.api.ChatColor.GOLD;
import static net.md_5.bungee.api.ChatColor.GRAY;
import static net.md_5.bungee.api.ChatColor.GREEN;
import static net.md_5.bungee.api.ChatColor.LIGHT_PURPLE;
import static net.md_5.bungee.api.ChatColor.RED;
import static net.md_5.bungee.api.ChatColor.WHITE;
import static net.md_5.bungee.api.ChatColor.YELLOW;

/**
 * See <a href="https://minecraft.fandom.com/wiki/Raw_JSON_text_format">Raw JSON text format</a>
 * article in Minecraft Wiki.
 *
 * @author Milkory
 */
@SuppressWarnings("unused")
public class JsonTextBuilder implements ContentBuilder<Text> {

    List<BaseComponent> components = new ArrayList<>();

    BaseComponent parent = new TextComponent();

    public static BaseComponent formats(BaseComponent component, Format[] formats) {
        for (Format format : formats) {
            switch (format) {
                case BOLD:
                    component.setBold(true);
                    break;
                case ITALIC:
                    component.setItalic(true);
                    break;
                case UNDERLINED:
                    component.setUnderlined(true);
                    break;
                case STRIKETHROUGH:
                    component.setStrikethrough(true);
                    break;
                case OBFUSCATED:
                    component.setObfuscated(true);
                    break;
            }
        }
        return component;
    }

    public BaseComponent[] components() {
        parent.setExtra(components);
        return new BaseComponent[]{parent};
    }

    public BaseComponent[] pureComponents() {
        return components.toArray(new BaseComponent[0]);
    }

    public JsonTextBuilder component(BaseComponent components) {
        this.components.add(components);
        return this;
    }

    public JsonTextBuilder component(BaseComponent... components) {
        this.components.addAll(Arrays.asList(components));
        return this;
    }

    public JsonTextBuilder component(BaseComponent component, Format... formats) {
        components.add(formats(component, formats));
        return this;
    }

    public JsonTextBuilder component(BaseComponent component, ChatColor color, Format... formats) {
        component.setColor(color);
        return component(component, formats);
    }

    public JsonTextBuilder component(BaseComponent component, String color, Format... formats) {
        return component(component, ChatColor.of(color), formats);
    }

    public JsonTextBuilder append(JsonTextBuilder builder) {
        return component(builder.components());
    }

    public JsonTextBuilder append(Consumer<JsonTextBuilder> consumer) {
        var builder = new JsonTextBuilder();
        consumer.accept(builder);
        return append(builder);
    }

    public JsonTextBuilder text(String text, Format... formats) {
        return component(new TextComponent(text), formats);
    }

    public JsonTextBuilder text(String text, ChatColor color, Format... formats) {
        return component(new TextComponent(text), color, formats);
    }

    public JsonTextBuilder text(String text, String color, Format... formats) {
        return component(new TextComponent(text), color, formats);
    }

    public JsonTextBuilder translate(String key, Format... formats) {
        return component(new TranslatableComponent(key), formats);
    }

    public JsonTextBuilder translate(String key, Object[] with, Format... formats) {
        return component(new TranslatableComponent(key, with), formats);
    }

    public JsonTextBuilder translate(String key, ChatColor color, Format... formats) {
        return component(new TranslatableComponent(key), color, formats);
    }

    public JsonTextBuilder translate(String key, Object[] with, ChatColor color, Format... formats) {
        return component(new TranslatableComponent(key, with), color, formats);
    }

    public JsonTextBuilder translate(String key, Object[] with, String color, Format... formats) {
        return component(new TranslatableComponent(key, with), color, formats);
    }

    public JsonTextBuilder keybind(String keybind, Format... formats) {
        return component(new KeybindComponent(keybind), formats);
    }

    public JsonTextBuilder keybind(String keybind, ChatColor color, Format... formats) {
        return component(new KeybindComponent(keybind), color, formats);
    }

    public JsonTextBuilder keybind(String keybind, String color, Format... formats) {
        return component(new KeybindComponent(keybind), color, formats);
    }

    public JsonTextBuilder newLine() {
        return text("\n");
    }

    /**
     * Set the global color.
     */
    public JsonTextBuilder color(ChatColor color) {
        parent.setColor(color);
        return this;
    }

    /**
     * Set the global color.
     */
    public JsonTextBuilder color(String hex) {
        var color = ChatColor.of(hex);
        return color(color);
    }

    public JsonTextBuilder click(ClickEvent event) {
        parent.setClickEvent(event);
        return this;
    }

    public JsonTextBuilder click(ClickEvent.Action action, String value) {
        return click(new ClickEvent(action, value));
    }

    public JsonTextBuilder hover(HoverEvent event) {
        parent.setHoverEvent(event);
        return this;
    }

    public JsonTextBuilder hover(Content content) {
        return hover(new HoverEvent(content.requiredAction(), content));
    }

    public JsonTextBuilder hover(ContentBuilder<?> builder) {
        return hover(builder.buildContent());
    }

    public JsonTextBuilder hover(Function<ContentBuilder.Provider, ContentBuilder<?>> function) {
        return hover(function.apply(ContentBuilder.provider()));
    }

    public JsonTextBuilder insertion(String insertion) {
        parent.setInsertion(insertion);
        return this;
    }

    // region Less-used content types

    public JsonTextBuilder font(String font) {
        parent.setFont(font);
        return this;
    }

    public JsonTextBuilder score(String name, String objective, Format... formats) {
        return component(new ScoreComponent(name, objective), formats);
    }

    public JsonTextBuilder score(String name, String objective, ChatColor color, Format... formats) {
        return component(new ScoreComponent(name, objective), color, formats);
    }

    public JsonTextBuilder score(String name, String objective, String color, Format... formats) {
        return component(new ScoreComponent(name, objective), color, formats);
    }

    public JsonTextBuilder selector(String selector, Format... formats) {
        return component(new SelectorComponent(selector), formats);
    }

    public JsonTextBuilder selector(String selector, ChatColor color, Format... formats) {
        return component(new SelectorComponent(selector), color, formats);
    }

    // endregion

    // region Add text with format

    public JsonTextBuilder selector(String selector, String color, Format... formats) {
        return component(new SelectorComponent(selector), color, formats);
    }

    public JsonTextBuilder bold(String text) {
        return text(text, Format.BOLD);
    }

    public JsonTextBuilder italic(String text) {
        return text(text, Format.ITALIC);
    }

    public JsonTextBuilder underlined(String text) {
        return text(text, Format.UNDERLINED);
    }

    public JsonTextBuilder strikethrough(String text) {
        return text(text, Format.STRIKETHROUGH);
    }

    // endregion

    // region Set the global format

    public JsonTextBuilder obfuscated(String text) {
        return text(text, Format.OBFUSCATED);
    }

    /**
     * Make all components included bold.
     */
    public JsonTextBuilder bold() {
        parent.setBold(true);
        return this;
    }

    /**
     * Make all components included italic.
     */
    public JsonTextBuilder italic() {
        parent.setItalic(true);
        return this;
    }

    /**
     * Make all components included underlined.
     */
    public JsonTextBuilder underlined() {
        parent.setUnderlined(true);
        return this;
    }

    /**
     * Make all components included strikethrough.
     */
    public JsonTextBuilder strikethrough() {
        parent.setStrikethrough(true);
        return this;
    }

    // endregion

    // region Add text with color

    /**
     * Make all components included obfuscated.
     */
    public JsonTextBuilder obfuscated() {
        parent.setObfuscated(true);
        return this;
    }

    public JsonTextBuilder black(String text, Format... formats) {
        return text(text, BLACK, formats);
    }

    public JsonTextBuilder darkBlue(String text, Format... formats) {
        return text(text, DARK_BLUE, formats);
    }

    public JsonTextBuilder darkGreen(String text, Format... formats) {
        return text(text, DARK_GREEN, formats);
    }

    public JsonTextBuilder darkAqua(String text, Format... formats) {
        return text(text, DARK_AQUA, formats);
    }

    public JsonTextBuilder darkRed(String text, Format... formats) {
        return text(text, DARK_RED, formats);
    }

    public JsonTextBuilder darkPurple(String text, Format... formats) {
        return text(text, DARK_PURPLE, formats);
    }

    public JsonTextBuilder gold(String text, Format... formats) {
        return text(text, GOLD, formats);
    }

    public JsonTextBuilder gray(String text, Format... formats) {
        return text(text, GRAY, formats);
    }

    public JsonTextBuilder darkGray(String text, Format... formats) {
        return text(text, DARK_GRAY, formats);
    }

    public JsonTextBuilder blue(String text, Format... formats) {
        return text(text, BLUE, formats);
    }

    public JsonTextBuilder green(String text, Format... formats) {
        return text(text, GREEN, formats);
    }

    public JsonTextBuilder aqua(String text, Format... formats) {
        return text(text, AQUA, formats);
    }

    public JsonTextBuilder red(String text, Format... formats) {
        return text(text, RED, formats);
    }

    public JsonTextBuilder lightPurple(String text, Format... formats) {
        return text(text, LIGHT_PURPLE, formats);
    }

    public JsonTextBuilder yellow(String text, Format... formats) {
        return text(text, YELLOW, formats);
    }

    // endregion

    // region Set the global color

    public JsonTextBuilder white(String text, Format... formats) {
        return text(text, WHITE, formats);
    }

    public JsonTextBuilder black() {
        return color(BLACK);
    }

    public JsonTextBuilder darkBlue() {
        return color(DARK_BLUE);
    }

    public JsonTextBuilder darkGreen() {
        return color(DARK_GREEN);
    }

    public JsonTextBuilder darkAqua() {
        return color(DARK_AQUA);
    }

    public JsonTextBuilder darkRed() {
        return color(DARK_RED);
    }

    public JsonTextBuilder darkPurple() {
        return color(DARK_PURPLE);
    }

    public JsonTextBuilder gold() {
        return color(GOLD);
    }

    public JsonTextBuilder gray() {
        return color(GRAY);
    }

    public JsonTextBuilder darkGray() {
        return color(DARK_GRAY);
    }

    public JsonTextBuilder blue() {
        return color(BLUE);
    }

    public JsonTextBuilder green() {
        return color(GREEN);
    }

    public JsonTextBuilder aqua() {
        return color(AQUA);
    }

    public JsonTextBuilder red() {
        return color(RED);
    }

    public JsonTextBuilder lightPurple() {
        return color(LIGHT_PURPLE);
    }

    public JsonTextBuilder yellow() {
        return color(YELLOW);
    }

    // endregion

    public JsonTextBuilder white() {
        return color(WHITE);
    }

    public String raw() {
        return ComponentSerializer.toString(components());
    }

    public String plain() {
        return TextComponent.toPlainText(components());
    }

    @Override
    public String toString() {
        return raw();
    }

    @Override
    public Text buildContent() {
        return new Text(components());
    }
}
