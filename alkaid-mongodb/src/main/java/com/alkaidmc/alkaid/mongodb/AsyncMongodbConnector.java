package com.alkaidmc.alkaid.mongodb;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;

public class AsyncMongodbConnector {
    MongoDatabase database;

    public AsyncMongodbConnector(MongoDatabase database) {
        this.database = database;
    }

    public AsyncMongodbConnection connection(Gson gson, String collection) {
        return new AsyncMongodbConnection(gson, database.getCollection(collection));
    }
}
