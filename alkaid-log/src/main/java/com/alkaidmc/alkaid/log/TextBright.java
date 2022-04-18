package com.alkaidmc.alkaid.log;

import org.fusesource.jansi.Ansi;

@SuppressWarnings("unused")
public class TextBright {
    public static String black(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.BLACK).a(text).reset().toString();
    }

    public static String red(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.RED).a(text).reset().toString();
    }

    public static String green(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.GREEN).a(text).reset().toString();
    }

    public static String yellow(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.YELLOW).a(text).reset().toString();
    }

    public static String blue(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.BLUE).a(text).reset().toString();
    }

    public static String magenta(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.MAGENTA).a(text).reset().toString();
    }

    public static String cyan(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.CYAN).a(text).reset().toString();
    }

    public static String white(String text) {
        return Ansi.ansi().fgBright(Ansi.Color.WHITE).a(text).reset().toString();
    }
}
