package com.alkaidmc.alkaid.web.client;

import okhttp3.OkHttpClient;

@SuppressWarnings("unused")
public class AlkaidOkhttpHttpClient {
    static OkHttpClient client;

    static {
        client = new OkHttpClient();
    }
}
