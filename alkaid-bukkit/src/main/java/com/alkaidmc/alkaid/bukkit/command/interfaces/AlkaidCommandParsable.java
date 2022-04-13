package com.alkaidmc.alkaid.bukkit.command.interfaces;

import java.util.function.Consumer;

public interface AlkaidCommandParsable {
    AlkaidCommandParsable parser(String command, Consumer<AlkaidCommandParser> consumer);
}
