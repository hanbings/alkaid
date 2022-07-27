package com.alkaidmc.alkaid.web.router;

import io.javalin.Javalin;
import io.javalin.websocket.WsBinaryMessageContext;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsErrorContext;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.function.Consumer;

@Setter
@Getter
@Accessors(fluent = true, chain = true)
@SuppressWarnings("unused SpellCheckingInspection")
public class AlkaidJavalinWebsocketRouter {
    final Javalin app;
    final String path;
    Consumer<WsBinaryMessageContext> binary = message -> {
    };
    Consumer<WsBinaryMessageContext> text = message -> {
    };
    Consumer<WsConnectContext> connect = message -> {
    };
    Consumer<WsCloseContext> close = message -> {
    };
    Consumer<WsErrorContext> error = message -> {
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
