package com.alkaidmc.alkaid.web.server;

@SuppressWarnings("unused SpellCheckingInspection")
public class AlkaidWebServer {
    public AlkaidJavalinServer javalin() {
        return new AlkaidJavalinServer();
    }

    public AlkaidVertxServer vertx() {
        return new AlkaidVertxServer();
    }
}
