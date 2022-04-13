package com.alkaidmc.alkaid.bukkit.command;

import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandParsable;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidCommandParser;
import com.alkaidmc.alkaid.bukkit.command.interfaces.AlkaidParserCallback;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.Consumer;

public class EnhanceCommandParser implements AlkaidCommandParser, AlkaidCommandParsable {
    CommandSender sender;
    String[] args;

    public EnhanceCommandParser(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public AlkaidCommandParser use(AlkaidParserCallback callback) {
        return null;
    }

    @Override
    public AlkaidCommandParser player(boolean filter) {
        return null;
    }

    @Override
    public AlkaidCommandParser console(boolean filter) {
        return null;
    }

    @Override
    public AlkaidCommandParser op(boolean filter) {
        return null;
    }

    @Override
    public AlkaidCommandParser tab(List<String> tab) {
        return null;
    }

    @Override
    public AlkaidCommandParser tab(String... tab) {
        return null;
    }

    @Override
    public AlkaidCommandParsable parse(Consumer<AlkaidCommandParser> consumer) {
        return null;
    }

    @Override
    public AlkaidCommandParsable parser(String command, Consumer<AlkaidCommandParser> consumer) {
        return null;
    }
}
