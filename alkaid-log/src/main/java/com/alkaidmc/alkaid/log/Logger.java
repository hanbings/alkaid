package com.alkaidmc.alkaid.log;

import lombok.Builder;
import org.fusesource.jansi.Ansi;

@SuppressWarnings("unused")
public class Logger {
    static java.util.logging.Logger logger;
    static String prefix;
    static Ansi.Color info;
    static Ansi.Color warning;
    static Ansi.Color severe;
    static Ansi.Color reset = Ansi.Color.DEFAULT;

    static {
        logger = java.util.logging.Logger.getLogger("Alkaid");
        prefix = "[Alkaid] ";
        info = Ansi.Color.GREEN;
        warning = Ansi.Color.YELLOW;
        severe = Ansi.Color.RED;
    }

    @Builder
    public Logger(String prefix, Ansi.Color info, Ansi.Color warning, Ansi.Color severe) {
        Logger.prefix = prefix;
        Logger.info = info;
        Logger.warning = warning;
        Logger.severe = severe;
    }

    public static void info(String msg) {
        logger.info(info + prefix + reset + msg);
    }

    public static void warning(String msg) {
        logger.warning(warning + prefix + reset + msg);
    }

    public static void severe(String msg) {
        logger.severe(severe + prefix + reset + msg);
    }

}
