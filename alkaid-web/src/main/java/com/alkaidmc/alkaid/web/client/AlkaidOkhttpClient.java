package com.alkaidmc.alkaid.web.client;

import okhttp3.OkHttpClient;

public class AlkaidOkhttpClient {
    static OkHttpClient client;

    static {
        client = new OkHttpClient();
    }

    public void get(String url, OkhttpResponse response) {

    }
}
