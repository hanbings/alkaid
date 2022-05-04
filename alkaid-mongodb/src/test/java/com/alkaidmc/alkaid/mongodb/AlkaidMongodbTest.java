package com.alkaidmc.alkaid.mongodb;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

// todo mongodb 测试
public class AlkaidMongodbTest {
    SyncMongodbConnection syncMongodbConnection = new AlkaidMongodb().database("test").sync();
    String testCollection = "testAlkaid";

    @Test
    public void insertionTest() {
        syncMongodbConnection.create(testCollection,
                new Document("name", "Neko")
                        .append("age", 4)
        );
    }

    @Test
    public void updateTest() {
        syncMongodbConnection.update(testCollection,
                new HashMap<>() {{
                    put("name", "Neko");
                }},
                new Document("$set", new Document("age", 3))
        );
    }

    @Test
    public void deleteTest() {
        syncMongodbConnection.delete(testCollection,
                new HashMap<>() {{
                    put("name", "Neko");
                }}
        );
    }

    @Test
    public void readTest() {
        syncMongodbConnection.read(testCollection, new Document("name", "Neko"), Object.class).forEach(System.out::println);
    }
}
