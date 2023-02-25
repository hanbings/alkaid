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

import com.alkaidmc.alkaid.mongodb.interfaces.Connection;
import com.alkaidmc.alkaid.mongodb.interfaces.Serialization;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class MongodbConnection implements Connection {
    final Serialization serialization;
    final MongoDatabase database;
    final ExecutorService pool;

    @Override
    public CompletableFuture<InsertOneResult> create(String collection, Object data) {
        return CompletableFuture.supplyAsync(
                () -> database.getCollection(collection)
                        .insertOne(Document.parse(serialization.serialize(data))),
                pool
        );
    }

    @Override
    public CompletableFuture<InsertManyResult> create(String collection, List<Object> data) {
        return CompletableFuture.supplyAsync(
                () -> database.getCollection(collection)
                        .insertMany(
                                data.stream()
                                        .map(d -> Document.parse(serialization.serialize(d)))
                                        .collect(Collectors.toList())
                        ),
                pool
        );
    }

    @Override
    public CompletableFuture<UpdateResult> update(String collection, Map<String, Object> index, Object data) {
        return CompletableFuture.supplyAsync(
                () -> database.getCollection(collection)
                        .updateMany(
                                Document.parse(serialization.map(index)),
                                Document.parse(serialization.serialize(data))
                        ),
                pool
        );
    }

    @Override
    public CompletableFuture<DeleteResult> delete(String collection, Map<String, Object> index) {
        return CompletableFuture.supplyAsync(
                () -> database.getCollection(collection)
                        .deleteMany(Document.parse(serialization.map(index))),
                pool
        );
    }

    @Override
    public <T> CompletableFuture<List<T>> read(String collection, Map<String, Object> index, Class<T> type) {
        return CompletableFuture.supplyAsync(
                () -> {
                    List<T> list = new ArrayList<>();

                    // get iterable
                    MongoCursor<Document> documents = database.getCollection(collection)
                            .find(Document.parse(serialization.map(index)))
                            .iterator();

                    // for each
                    documents.forEachRemaining(document -> {
                        // 将 document 转换回对象
                        list.add(serialization.deserialize(type, document));
                    });

                    // close iterable
                    documents.close();

                    return list;
                },
                pool
        );
    }

    @Override
    public <T, V> CompletableFuture<List<T>> search(String collection, String data, V top, V bottom, int limit, Class<T> type) {
        return CompletableFuture.supplyAsync(
                () -> {
                    List<T> list = new ArrayList<>();

                    // get iterable
                    MongoCursor<Document> documents = database.getCollection(collection)
                            .find(new BasicDBObject() {{
                                put(data, new BasicDBObject() {{
                                    put("$gte", top);
                                    put("$lte", bottom);
                                }});
                            }})
                            .skip(0)
                            .limit(limit)
                            .iterator();

                    // for each
                    documents.forEachRemaining(document -> {
                        // 将 document 转换回对象
                        list.add(serialization.deserialize(type, document));
                    });

                    // close iterable
                    documents.close();

                    return list;
                },
                pool
        );
    }
}
