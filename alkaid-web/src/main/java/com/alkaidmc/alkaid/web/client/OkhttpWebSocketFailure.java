package com.alkaidmc.alkaid.web.client;

import okhttp3.Response;
import okhttp3.WebSocket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
@SuppressWarnings("unused")
public interface OkhttpWebSocketFailure {
    void failure(@NotNull WebSocket websocket, @NotNull Throwable t, @Nullable Response response);
}
