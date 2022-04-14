package com.alkaidmc.alkaid.bukkit.log;

import org.fusesource.jansi.Ansi;

@SuppressWarnings("unused")
public class TextEffect {
    public static String underline(String text) {
        return Ansi.ansi().a(Ansi.Attribute.UNDERLINE).a(text).reset().toString();
    }

    public static String bold(String text) {
        return Ansi.ansi().a(Ansi.Attribute.INTENSITY_BOLD).a(text).reset().toString();
    }

    public static String negative(String text) {
        return Ansi.ansi().a(Ansi.Attribute.NEGATIVE_ON).a(text).reset().toString();
    }
}
