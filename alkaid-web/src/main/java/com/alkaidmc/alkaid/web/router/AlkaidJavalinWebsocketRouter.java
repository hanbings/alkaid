package com.alkaidmc.alkaid.web.router;

import io.javalin.Javalin;
import io.javalin.websocket.WsBinaryMessageContext;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsErrorContext;

import java.util.function.Consumer;

@SuppressWarnings("unused SpellCheckingInspection")
public class AlkaidJavalinWebsocketRouter {
    final Javalin app;
    final String path;
    final Consumer<WsBinaryMessageContext> binary = message -> {
    };
    final Consumer<WsBinaryMessageContext> text = message -> {
    };
    final Consumer<WsConnectContext> connect = message -> {
    };
    final Consumer<WsCloseContext> close = message -> {
    };
    final Consumer<WsErrorContext> error = message -> {
    };

    public AlkaidJavalinWebsocketRouter(Javalin app, String path) {
        this.app = app;
        this.path = path;

        app.ws(path, ctx -> {
            ctx.onBinaryMessage(binary::accept);
            ctx.onBinaryMessage(text::accept);
            ctx.onConnect(connect::accept);
            ctx.onClose(close::accept);
            ctx.onError(error::accept);
        });
    }
}
