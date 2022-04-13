package com.alkaidmc.alkaid.bukkit.command.interfaces;

public interface AlkaidCommandParser {
    AlkaidCommandParser parser(String command);

    AlkaidCommandParser filter(boolean player);

    AlkaidCommandParser use(AlkaidParserCallback callback);
}
