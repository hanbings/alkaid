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
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static net.md_5.bungee.api.ChatColor.*;

/**
 * See <a href="https://minecraft.fandom.com/wiki/Raw_JSON_text_format">Raw JSON text format</a>
 * article in Minecraft Wiki.
 *
 * @author Milkory
 */
@SuppressWarnings("unused")
public class JSONTextBuilder implements ContentBuilder<Text> {

    List<BaseComponent> components = new ArrayList<>();

    BaseComponent parent = new TextComponent();

    public BaseComponent[] components() {
        parent.setExtra(components);
        return new BaseComponent[]{parent};
    }

    public BaseComponent[] pureComponents() {
        return components.toArray(new BaseComponent[0]);
    }

    public JSONTextBuilder component(BaseComponent components) {
        this.components.add(components);
        return this;
    }

    public JSONTextBuilder component(BaseComponent... components) {
        this.components.addAll(Arrays.asList(components));
        return this;
    }

    public JSONTextBuilder component(BaseComponent component, Format... formats) {
        components.add(addFormats(component, formats));
        return this;
    }

    public JSONTextBuilder component(BaseComponent component, ChatColor color, Format... formats) {
        component.setColor(color);
        return component(component, formats);
    }

    public JSONTextBuilder component(BaseComponent component, String color, Format... formats) {
        return component(component, ChatColor.of(color), formats);
    }

    public JSONTextBuilder append(JSONTextBuilder builder) {
        return component(builder.components());
    }

    public JSONTextBuilder append(Consumer<JSONTextBuilder> consumer) {
        var builder = new JSONTextBuilder();
        consumer.accept(builder);
        return append(builder);
    }

    public JSONTextBuilder text(String text, Format... formats) {
        return component(new TextComponent(text), formats);
    }

    public JSONTextBuilder text(String text, ChatColor color, Format... formats) {
        return component(new TextComponent(text), color, formats);
    }

    public JSONTextBuilder text(String text, String color, Format... formats) {
        return component(new TextComponent(text), color, formats);
    }

    public JSONTextBuilder translate(String key, Format... formats) {
        return component(new TranslatableComponent(key), formats);
    }

    public JSONTextBuilder translate(String key, Object[] with, Format... formats) {
        return component(new TranslatableComponent(key, with), formats);
    }

    public JSONTextBuilder translate(String key, ChatColor color, Format... formats) {
        return component(new TranslatableComponent(key), color, formats);
    }

    public JSONTextBuilder translate(String key, Object[] with, ChatColor color, Format... formats) {
        return component(new TranslatableComponent(key, with), color, formats);
    }

    public JSONTextBuilder translate(String key, Object[] with, String color, Format... formats) {
        return component(new TranslatableComponent(key, with), color, formats);
    }

    public JSONTextBuilder keybind(String keybind, Format... formats) {
        return component(new KeybindComponent(keybind), formats);
    }

    public JSONTextBuilder keybind(String keybind, ChatColor color, Format... formats) {
        return component(new KeybindComponent(keybind), color, formats);
    }

    public JSONTextBuilder keybind(String keybind, String color, Format... formats) {
        return component(new KeybindComponent(keybind), color, formats);
    }

    public JSONTextBuilder newLine() {
        return text("\n");
    }

    public JSONTextBuilder modify(Consumer<BaseComponent> consumer) {
        consumer.accept(parent);
        return this;
    }

    /** Set the global color. */
    public JSONTextBuilder color(ChatColor color) {
        return modify(it -> it.setColor(color));
    }

    /** Set the global color. */
    public JSONTextBuilder color(String hex) {
        var color = ChatColor.of(hex);
        return color(color);
    }

    public JSONTextBuilder clickEvent(ClickEvent event) {
        return modify(it -> it.setClickEvent(event));
    }

    public JSONTextBuilder clickEvent(ClickEvent.Action action, String value) {
        return clickEvent(new ClickEvent(action, value));
    }

    public JSONTextBuilder hoverEvent(HoverEvent event) {
        return modify(it -> it.setHoverEvent(event));
    }

    public JSONTextBuilder hoverEvent(Content content) {
        return hoverEvent(new HoverEvent(content.requiredAction(), content));
    }

    public JSONTextBuilder hoverEvent(ContentBuilder<?> builder) {
        return hoverEvent(builder.buildContent());
    }

    public JSONTextBuilder hoverEvent(Function<ContentBuilder.Provider, ContentBuilder<?>> function) {
        return hoverEvent(function.apply(ContentBuilder.provider()));
    }

    public JSONTextBuilder insertion(String insertion) {
        return modify(it -> it.setInsertion(insertion));
    }

    public JSONTextBuilder font(String font) {
        return modify(it -> it.setFont(font));
    }

    // region Less-used content types

    public JSONTextBuilder score(String name, String objective, Format... formats) {
        return component(new ScoreComponent(name, objective), formats);
    }

    public JSONTextBuilder score(String name, String objective, ChatColor color, Format... formats) {
        return component(new ScoreComponent(name, objective), color, formats);
    }

    public JSONTextBuilder score(String name, String objective, String color, Format... formats) {
        return component(new ScoreComponent(name, objective), color, formats);
    }

    public JSONTextBuilder selector(String selector, Format... formats) {
        return component(new SelectorComponent(selector), formats);
    }

    public JSONTextBuilder selector(String selector, ChatColor color, Format... formats) {
        return component(new SelectorComponent(selector), color, formats);
    }

    public JSONTextBuilder selector(String selector, String color, Format... formats) {
        return component(new SelectorComponent(selector), color, formats);
    }

    // endregion

    // region Add text with format

    public JSONTextBuilder bold(String text) {
        return text(text, Format.BOLD);
    }

    public JSONTextBuilder italic(String text) {
        return text(text, Format.ITALIC);
    }

    public JSONTextBuilder underlined(String text) {
        return text(text, Format.UNDERLINED);
    }

    public JSONTextBuilder strikethrough(String text) {
        return text(text, Format.STRIKETHROUGH);
    }

    public JSONTextBuilder obfuscated(String text) {
        return text(text, Format.OBFUSCATED);
    }

    // endregion

    // region Set the global format

    /** Make all components included bold. */
    public JSONTextBuilder bold() {
        return modify(it -> it.setBold(true));
    }

    /** Make all components included italic. */
    public JSONTextBuilder italic() {
        return modify(it -> it.setItalic(true));
    }

    /** Make all components included underlined. */
    public JSONTextBuilder underlined() {
        return modify(it -> it.setUnderlined(true));
    }

    /** Make all components included strikethrough. */
    public JSONTextBuilder strikethrough() {
        return modify(it -> it.setStrikethrough(true));
    }

    /** Make all components included obfuscated. */
    public JSONTextBuilder obfuscated() {
        return modify(it -> it.setObfuscated(true));
    }

    // endregion

    // region Add text with color

    public JSONTextBuilder black(String text, Format... formats) {
        return text(text, BLACK, formats);
    }

    public JSONTextBuilder darkBlue(String text, Format... formats) {
        return text(text, DARK_BLUE, formats);
    }

    public JSONTextBuilder darkGreen(String text, Format... formats) {
        return text(text, DARK_GREEN, formats);
    }

    public JSONTextBuilder darkAqua(String text, Format... formats) {
        return text(text, DARK_AQUA, formats);
    }

    public JSONTextBuilder darkRed(String text, Format... formats) {
        return text(text, DARK_RED, formats);
    }

    public JSONTextBuilder darkPurple(String text, Format... formats) {
        return text(text, DARK_PURPLE, formats);
    }

    public JSONTextBuilder gold(String text, Format... formats) {
        return text(text, GOLD, formats);
    }

    public JSONTextBuilder gray(String text, Format... formats) {
        return text(text, GRAY, formats);
    }

    public JSONTextBuilder darkGray(String text, Format... formats) {
        return text(text, DARK_GRAY, formats);
    }

    public JSONTextBuilder blue(String text, Format... formats) {
        return text(text, BLUE, formats);
    }

    public JSONTextBuilder green(String text, Format... formats) {
        return text(text, GREEN, formats);
    }

    public JSONTextBuilder aqua(String text, Format... formats) {
        return text(text, AQUA, formats);
    }

    public JSONTextBuilder red(String text, Format... formats) {
        return text(text, RED, formats);
    }

    public JSONTextBuilder lightPurple(String text, Format... formats) {
        return text(text, LIGHT_PURPLE, formats);
    }

    public JSONTextBuilder yellow(String text, Format... formats) {
        return text(text, YELLOW, formats);
    }

    public JSONTextBuilder white(String text, Format... formats) {
        return text(text, WHITE, formats);
    }

    // endregion

    // region Set the global color

    public JSONTextBuilder black() {
        return color(BLACK);
    }

    public JSONTextBuilder darkBlue() {
        return color(DARK_BLUE);
    }

    public JSONTextBuilder darkGreen() {
        return color(DARK_GREEN);
    }

    public JSONTextBuilder darkAqua() {
        return color(DARK_AQUA);
    }

    public JSONTextBuilder darkRed() {
        return color(DARK_RED);
    }

    public JSONTextBuilder darkPurple() {
        return color(DARK_PURPLE);
    }

    public JSONTextBuilder gold() {
        return color(GOLD);
    }

    public JSONTextBuilder gray() {
        return color(GRAY);
    }

    public JSONTextBuilder darkGray() {
        return color(DARK_GRAY);
    }

    public JSONTextBuilder blue() {
        return color(BLUE);
    }

    public JSONTextBuilder green() {
        return color(GREEN);
    }

    public JSONTextBuilder aqua() {
        return color(AQUA);
    }

    public JSONTextBuilder red() {
        return color(RED);
    }

    public JSONTextBuilder lightPurple() {
        return color(LIGHT_PURPLE);
    }

    public JSONTextBuilder yellow() {
        return color(YELLOW);
    }

    public JSONTextBuilder white() {
        return color(WHITE);
    }

    // endregion

    public static BaseComponent addFormats(BaseComponent component, Format[] formats) {
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

    public String raw() {
        return ComponentSerializer.toString(components());
    }

    public String plain() {
        return TextComponent.toPlainText(components());
    }

    @Override public String toString() {
        return raw();
    }

    @Override public Text buildContent() {
        return new Text(components());
    }
}
