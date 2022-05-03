package com.alkaidmc.alkaid.mongodb;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class AlkaidMongodbTest {
    AsyncMongodbConnection asyncMongodbConnection = new AlkaidMongodb().database("test").async();
    String testCollection = "testAlkaid";

    @Test
    public void insertionTest() {
        asyncMongodbConnection.create(testCollection,
                new Document("name", "Neko")
                        .append("age", 4)
        );
    }

    @Test
    public void updateTest() {
        asyncMongodbConnection.update(testCollection,
                Collections.singletonMap("name", "Neko"),
                new Document("$set", new Document("age", 3)));
    }

    @Test
    public void deleteTest() {
        asyncMongodbConnection.delete(testCollection,
                new Document("name", "Neko"));
    }
}
