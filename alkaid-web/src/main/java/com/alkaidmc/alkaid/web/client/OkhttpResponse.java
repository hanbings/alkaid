package com.alkaidmc.alkaid.web.client;

import lombok.RequiredArgsConstructor;
import okhttp3.Call;
import okhttp3.Response;

@RequiredArgsConstructor
public class OkhttpResponse {
    final Call call;
    final Response response;
    final Exception exception;

    public void susses(Call call, Response response) {

    }

    public void failure(Call call, Exception exception) {

    }
}
