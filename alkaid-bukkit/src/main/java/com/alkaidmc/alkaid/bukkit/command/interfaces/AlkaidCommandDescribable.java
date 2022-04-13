package com.alkaidmc.alkaid.bukkit.command.interfaces;

public interface AlkaidCommandDescribable {
    AlkaidCommandDescribable description(String description);

    AlkaidCommandDescribable usage(String usage);

    AlkaidCommandDescribable permission(String permission);

    AlkaidCommandDescribable alias(String alias);

    AlkaidCommandDescribable aliases(String... aliases);
}
