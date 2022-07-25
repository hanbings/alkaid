package com.alkaidmc.alkaid.web;

import com.alkaidmc.alkaid.web.server.AlkaidJavalinServer;

@SuppressWarnings("unused")
public class AlkaidWeb {
    @SuppressWarnings("SpellCheckingInspection")
    public AlkaidJavalinServer javalin() {
        return new AlkaidJavalinServer();
    }
}
