package com.alkaidmc.alkaid.mongodb;

import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoClient;

public class AsyncMongodbConnector {
    MongoClient client;
    MongoClientOptions.Builder options;
    String host;
    String port;
    String username;
    String password;

    public AsyncMongodbConnector(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
