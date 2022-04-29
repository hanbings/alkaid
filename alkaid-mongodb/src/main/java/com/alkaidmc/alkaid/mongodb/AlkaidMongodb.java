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

import java.util.Optional;

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
    MongoClientOptions options = MongoClientOptions.builder().build();

    // 托管 Client 的示例
    MongoClient client = null;

    // todo: mongodb 链接和鉴权
    public SyncMongodbConnection sync(String collection) {
        Optional.ofNullable(client).orElseGet(() -> {
            if (username != null && password != null) {
                MongoCredential credential =
                        MongoCredential.createCredential(username, database, password.toCharArray());
                return new MongoClient(new ServerAddress(host, port), credential, options);
            } else {
                return new MongoClient(new ServerAddress(host, port), options);
            }
        });
        return new SyncMongodbConnection(gson, client.getDatabase(database));
    }

    public AsyncMongodbConnection async(String collection) {
        Optional.ofNullable(client).orElseGet(() -> {
            if (username != null && password != null) {
                MongoCredential credential =
                        MongoCredential.createCredential(username, database, password.toCharArray());
                return new MongoClient(new ServerAddress(host, port), credential, options);
            } else {
                return new MongoClient(new ServerAddress(host, port), options);
            }
        });
        return new AsyncMongodbConnection(gson, client.getDatabase(database));
    }

    public void close() {
        Optional.ofNullable(client).ifPresent(MongoClient::close);
    }
}
