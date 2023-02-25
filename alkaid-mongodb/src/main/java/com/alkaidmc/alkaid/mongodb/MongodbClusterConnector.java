/*
 * Copyright 2023 Alkaid
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

import com.alkaidmc.alkaid.mongodb.interfaces.Connector;
import com.alkaidmc.alkaid.mongodb.interfaces.Serialization;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class MongodbClusterConnector implements Connector {
    List<ServerAddress> hosts;
    String database;
    String username;
    String password;
    ExecutorService pool = Executors.newFixedThreadPool(10);
    Serialization serialization = new GsonSerialization();
    MongoClientSettings settings = null;

    // 托管 Client 的实例
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    MongoClient client = null;

    @Override
    public MongodbClusterConnector connect() {
        client = Optional.ofNullable(client).orElseGet(() -> {
            if (username != null && password != null) {
                MongoCredential credential =
                        MongoCredential.createCredential(username, database, password.toCharArray());
                return MongoClients.create(
                        Optional.ofNullable(settings)
                                .orElse(
                                        MongoClientSettings.builder()
                                                .applyToClusterSettings(builder -> builder.hosts(hosts))
                                                .credential(credential)
                                                .build()
                                )
                );
            } else {
                return MongoClients.create(
                        Optional.ofNullable(settings)
                                .orElse(
                                        MongoClientSettings.builder()
                                                .applyToClusterSettings(builder -> builder.hosts(hosts))
                                                .build()
                                )
                );
            }
        });
        return this;
    }

    @Override
    public MongodbClusterConnection connection() {
        return new MongodbClusterConnection(serialization, client.getDatabase(database), pool);
    }

    @Override
    public void disconnect() {
        client.close();
    }
}
