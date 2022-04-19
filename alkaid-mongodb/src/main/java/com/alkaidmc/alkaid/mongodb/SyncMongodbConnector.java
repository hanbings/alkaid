package com.alkaidmc.alkaid.mongodb;

import com.mongodb.MongoClient;

public class SyncMongodbConnector {
    MongoClient client;

    public SyncMongodbConnector(MongoClient client) {
        this.client = client;
    }
}
