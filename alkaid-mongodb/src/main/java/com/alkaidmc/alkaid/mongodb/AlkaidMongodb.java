package com.alkaidmc.alkaid.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class AlkaidMongodb {
    String host;
    int port;
    String database;
    String username;
    String password;
    MongoClientOptions options;


    public AlkaidMongodb(String host, int port,
                         String database, String username, String password,
                         MongoClientOptions options) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.options = options;
    }

    public SyncMongodbConnector sync() {
        if (username != null && password != null) {
            MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
            return new SyncMongodbConnector(
                    new MongoClient(
                            new ServerAddress(host, port), credential, options
                    )
            );
        }
        return new SyncMongodbConnector(
                new MongoClient(
                        new ServerAddress(host, port), options
                )
        );
    }

    public AsyncMongodbConnector async() {
        if (username != null && password != null) {
            MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
            return new AsyncMongodbConnector(
                    new MongoClient(
                            new ServerAddress(host, port), credential, options
                    ).getDatabase(database)
            );
        }
        return new AsyncMongodbConnector(
                new MongoClient(
                        new ServerAddress(host, port), options
                ).getDatabase(database)
        );
    }
}
