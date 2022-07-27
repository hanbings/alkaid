package com.alkaidmc.alkaid.web.client;

import okhttp3.Response;
import okhttp3.WebSocket;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
@SuppressWarnings("unused")
public interface OkhttpWebSocketOpen {
    void open(@NotNull WebSocket websocket, @NotNull Response response);
}
