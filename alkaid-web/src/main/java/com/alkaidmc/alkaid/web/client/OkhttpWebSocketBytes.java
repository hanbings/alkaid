package com.alkaidmc.alkaid.web.client;

import okhttp3.WebSocket;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
@SuppressWarnings("unused")
public interface OkhttpWebSocketBytes {
    void bytes(@NotNull WebSocket websocket, @NotNull ByteString bytes);
}
