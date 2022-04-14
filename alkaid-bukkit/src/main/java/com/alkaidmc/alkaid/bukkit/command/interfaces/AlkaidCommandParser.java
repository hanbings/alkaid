package com.alkaidmc.alkaid.bukkit.command.interfaces;

import java.util.function.Consumer;

public interface AlkaidCommandParser {

    AlkaidCommandParser use(AlkaidParserCallback callback);

    AlkaidCommandParser tab(AlkaidTabCallback callback);

    AlkaidCommandParser player(boolean filter);

    AlkaidCommandParser console(boolean filter);

    AlkaidCommandParser op(boolean filter);

    AlkaidCommandParser parser(Consumer<AlkaidCommandParser> consumer);

    AlkaidCommandParser parser(String command, Consumer<AlkaidCommandParser> consumer);
}
