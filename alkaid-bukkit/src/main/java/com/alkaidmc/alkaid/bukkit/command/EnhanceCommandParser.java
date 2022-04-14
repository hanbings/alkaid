package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandParser;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidParserCallback;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidTabCallback;
import org.bukkit.command.CommandSender;

import java.util.function.Consumer;

public class EnhanceCommandParser implements AlkaidCommandParser {
    CommandSender sender;
    String[] args;

    public EnhanceCommandParser(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public EnhanceCommandParser use(AlkaidParserCallback callback) {
        return null;
    }

    @Override
    public EnhanceCommandParser tab(AlkaidTabCallback callback) {
        return null;
    }

    @Override
    public EnhanceCommandParser player(boolean filter) {
        return null;
    }

    @Override
    public EnhanceCommandParser console(boolean filter) {
        return null;
    }

    @Override
    public EnhanceCommandParser op(boolean filter) {
        return null;
    }

    @Override
    public EnhanceCommandParser parser(Consumer<AlkaidCommandParser> consumer) {
        return null;
    }

    @Override
    public EnhanceCommandParser parser(String command, Consumer<AlkaidCommandParser> consumer) {
        return null;
    }
}
