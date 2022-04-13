package com.alkaidmc.alkaid.bukkit.command.interfaces;

import java.util.List;

public interface AlkaidCommandParser {
    AlkaidCommandParser with(String command);

    AlkaidCommandParser use(AlkaidParserCallback callback);

    AlkaidCommandParser player(boolean filter);

    AlkaidCommandParser console(boolean filter);

    AlkaidCommandParser op(boolean filter);

    AlkaidCommandParser tab(List<String> tab);

    AlkaidCommandParser tab(String... tab);
}
