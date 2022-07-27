package com.alkaidmc.alkaid.web.client;

import okhttp3.WebSocket;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
@SuppressWarnings("unused")
public interface OkhttpWebSocketMessage {
    void message(@NotNull WebSocket websocket, @NotNull String text);
}
