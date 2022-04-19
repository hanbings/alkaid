package com.alkaidmc.alkaid.mongodb;

import com.alkaidmc.alkaid.mongodb.interfaces.AsyncQueryActions;
import com.alkaidmc.alkaid.mongodb.interfaces.WriteableActions;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AsyncMongodbConnection implements WriteableActions, AsyncQueryActions {
    Gson gson;
    MongoCollection<Document> collection;

    public AsyncMongodbConnection(Gson gson, MongoCollection<Document> collection) {
        this.gson = gson;
        this.collection = collection;
    }

    @Override
    public void create(Object data) {
        collection.insertOne(Document.parse(gson.toJson(data)));
    }

    @Override
    public void create(List<Object> data) {
        data.forEach(d -> collection.insertOne(Document.parse(gson.toJson(d))));
    }

    @Override
    public void update(Map<String, Object> index, Object data) {
        collection.updateMany(
                Document.parse(gson.toJsonTree(index, Map.class).toString()),
                Document.parse(gson.toJson(data))
        );
    }

    @Override
    public void delete(Map<String, Object> index) {
        collection.deleteMany(Document.parse(gson.toJsonTree(index, Map.class).toString()));
    }

    @Override
    public <T> void read(Map<String, Object> index, Class<T> type, Consumer<List<T>> consumer) {
        List<T> list = new ArrayList<>();
        // 获取数据库连接
        collection.find(Document.parse(gson.toJsonTree(index, Map.class).toString()))
                .iterator()
                .forEachRemaining(document -> {
                    // 将 document 转换回对象
                    list.add(gson.fromJson(document.toJson(), type));
                });
        consumer.accept(list);
    }

    @Override
    public <T, V> void search(String data, V top, V bottom, int limit, Class<T> type, Consumer<List<T>> consumer) {
        List<T> list = new ArrayList<>();
        // 获取数据库连接
        collection
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
        consumer.accept(list);
    }
}
