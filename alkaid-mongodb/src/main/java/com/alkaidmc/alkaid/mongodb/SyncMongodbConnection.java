package com.alkaidmc.alkaid.mongodb;

import com.alkaidmc.alkaid.mongodb.interfaces.SyncQueryActions;
import com.alkaidmc.alkaid.mongodb.interfaces.WriteableActions;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyncMongodbConnection implements WriteableActions, SyncQueryActions {
    Gson gson;
    MongoDatabase database;
    String collection;

    public SyncMongodbConnection(Gson gson, MongoDatabase database, String collection) {
        this.gson = gson;
        this.database = database;
        this.collection = collection;
    }

    @Override
    public void create(Object data) {
        database.getCollection(collection).insertOne(Document.parse(gson.toJson(data)));
    }

    @Override
    public void create(List<Object> data) {
        data.forEach(d -> database
                .getCollection(collection)
                .insertOne(Document.parse(gson.toJson(d))));
    }

    @Override
    public void update(Map<String, Object> index, Object data) {
        database.getCollection(collection).updateMany(
                Document.parse(gson.toJsonTree(index, Map.class).toString()),
                Document.parse(gson.toJson(data))
        );
    }

    @Override
    public void delete(Map<String, Object> index) {
        database.getCollection(collection)
                .deleteMany(Document.parse(gson.toJsonTree(index, Map.class).toString()));
    }

    @Override
    public <T> List<T> read(Map<String, Object> index, Class<T> type) {
        List<T> list = new ArrayList<>();
        // 获取数据库连接
        database.getCollection(collection)
                .find(Document.parse(gson.toJsonTree(index, Map.class).toString()))
                .iterator()
                .forEachRemaining(document -> {
                    // 将 document 转换回对象
                    list.add(gson.fromJson(document.toJson(), type));
                });
        return list;
    }

    @Override
    public <T, V> List<T> search(String data, V top, V bottom, int limit, Class<T> type) {
        List<T> list = new ArrayList<>();
        // 获取数据库连接
        database.getCollection(collection)
                .find(new BasicDBObject() {{
                    put(data, new BasicDBObject() {{
                        put("$gte", top);
                        put("$lte", bottom);
                    }});
                }})
                .skip(0)
                .limit(limit)
                .iterator()
                .forEachRemaining(document -> {
                    // 将 document 转换回对象
                    list.add(gson.fromJson(document.toJson(), type));
                });
        return list;
    }
}
