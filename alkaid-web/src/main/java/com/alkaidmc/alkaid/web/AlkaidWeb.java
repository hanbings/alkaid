package com.alkaidmc.alkaid.web;

import com.alkaidmc.alkaid.web.client.AlkaidWebClient;
import com.alkaidmc.alkaid.web.server.AlkaidWebServer;

@SuppressWarnings("unused")
public class AlkaidWeb {
    public AlkaidWebServer server() {
        return new AlkaidWebServer();
    }

    public AlkaidWebClient client() {
        return new AlkaidWebClient();
    }
}
