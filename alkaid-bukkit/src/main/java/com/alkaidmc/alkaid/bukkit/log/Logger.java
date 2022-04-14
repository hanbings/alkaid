package com.alkaidmc.alkaid.bukkit.log;

import lombok.Builder;
import org.bukkit.ChatColor;

public class Logger {
    static java.util.logging.Logger logger;
    static String prefix;
    static ChatColor info;
    static ChatColor warning;
    static ChatColor severe;
    static ChatColor reset = ChatColor.RESET;

    static {
        logger = java.util.logging.Logger.getLogger("Alkaid");
        prefix = "[Alkaid] ";
        info = ChatColor.GREEN;
        warning = ChatColor.YELLOW;
        severe = ChatColor.RED;
    }

    @Builder
    public Logger(String prefix, ChatColor info, ChatColor warning, ChatColor severe) {
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
