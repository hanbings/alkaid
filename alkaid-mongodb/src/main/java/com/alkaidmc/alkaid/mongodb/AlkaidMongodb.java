package com.alkaidmc.alkaid.mongodb;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
public class AlkaidMongodb {
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String host;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    int port;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String database;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String username;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String password;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    Gson gson = new Gson();
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    MongoClientOptions options = null;

    public SyncMongodbConnection sync(String collection) {
        return null;
    }

    public AsyncMongodbConnection async(String collection) {
       return null;
    }
}
