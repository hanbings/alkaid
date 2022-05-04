/*
 * Copyright 2022 Alkaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
@SuppressWarnings("unused")
public class AlkaidMongodb {
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String host;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    int port = 27017;
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

    public SyncMongodbConnection sync() {
        client = Optional.ofNullable(client).orElseGet(() -> {
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

    public AsyncMongodbConnection async() {
        client = Optional.ofNullable(client).orElseGet(() -> {
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
