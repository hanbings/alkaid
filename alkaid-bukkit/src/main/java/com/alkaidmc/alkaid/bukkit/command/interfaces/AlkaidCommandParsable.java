package com.alkaidmc.alkaid.bukkit.command.interfaces;

import java.util.function.Consumer;

public interface AlkaidCommandParsable {
    AlkaidCommandParsable parse(Consumer<AlkaidCommandParser> consumer);

    AlkaidCommandParsable parser(String command, Consumer<AlkaidCommandParser> consumer);
}
