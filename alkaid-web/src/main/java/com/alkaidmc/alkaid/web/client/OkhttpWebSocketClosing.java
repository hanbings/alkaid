package com.alkaidmc.alkaid.web.client;

import okhttp3.WebSocket;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
@SuppressWarnings("unused")
public interface OkhttpWebSocketClosing {
    void closing(@NotNull WebSocket websocket, int code, @NotNull String reason);
}
