package com.alkaidmc.alkaid.web.client;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class AlkaidOkhttpWebSocketClient {
    static OkHttpClient client;

    static {
        client = new OkHttpClient();
    }

    WebSocket websocket;
    String url;
    boolean async = true;
    OkhttpWebSocketOpen open = (websocket, response) -> {
    };
    OkhttpWebSocketBytes bytes = (websocket, bytes) -> {
    };
    OkhttpWebSocketMessage message = (websocket, text) -> {
    };
    OkhttpWebSocketFailure failure = (websocket, t, response) -> {
    };
    OkhttpWebSocketClosing closing = (websocket, code, reason) -> {
    };
    OkhttpWebSocketClosed closed = (websocket, code, reason) -> {
    };

    public AlkaidOkhttpWebSocketClient connect() {
        websocket = client.newWebSocket(new Request.Builder().url(url).build(), new WebSocketListener() {
            @Override
            public void onOpen(@NotNull WebSocket websocket, @NotNull Response response) {
                open.open(websocket, response);
            }

            @Override
            public void onMessage(@NotNull WebSocket websocket, @NotNull String text) {
                message.message(websocket, text);
            }

            @Override
            public void onMessage(@NotNull WebSocket websocket, @NotNull ByteString b) {
                bytes.bytes(websocket, b);
            }

            @Override
            public void onFailure(@NotNull WebSocket websocket, @NotNull Throwable t, @Nullable Response response) {
                failure.failure(websocket, t, response);
            }

            @Override
            public void onClosing(@NotNull WebSocket websocket, int code, @NotNull String reason) {
                closing.closing(websocket, code, reason);
            }

            @Override
            public void onClosed(@NotNull WebSocket websocket, int code, @NotNull String reason) {
                closed.closed(websocket, code, reason);
            }
        });

        return this;
    }

    public void send(String message) {
        websocket.send(message);
    }

    public void send(ByteString message) {
        websocket.send(message);
    }
}
