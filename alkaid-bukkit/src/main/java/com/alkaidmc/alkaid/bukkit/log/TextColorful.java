package com.alkaidmc.alkaid.bukkit.log;

import org.fusesource.jansi.Ansi;

@SuppressWarnings("unused")
public class TextColorful {
    public static String black(String text) {
        return Ansi.ansi().fg(Ansi.Color.BLACK).a(text).reset().toString();
    }

    public static String red(String text) {
        return Ansi.ansi().fg(Ansi.Color.RED).a(text).reset().toString();
    }

    public static String green(String text) {
        return Ansi.ansi().fg(Ansi.Color.GREEN).a(text).reset().toString();
    }

    public static String yellow(String text) {
        return Ansi.ansi().fg(Ansi.Color.YELLOW).a(text).reset().toString();
    }

    public static String blue(String text) {
        return Ansi.ansi().fg(Ansi.Color.BLUE).a(text).reset().toString();
    }

    public static String magenta(String text) {
        return Ansi.ansi().fg(Ansi.Color.MAGENTA).a(text).reset().toString();
    }

    public static String cyan(String text) {
        return Ansi.ansi().fg(Ansi.Color.CYAN).a(text).reset().toString();
    }

    public static String white(String text) {
        return Ansi.ansi().fg(Ansi.Color.WHITE).a(text).reset().toString();
    }
}
