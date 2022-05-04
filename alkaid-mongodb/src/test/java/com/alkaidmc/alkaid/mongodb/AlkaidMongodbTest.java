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

import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

// todo mongodb 测试
@SuppressWarnings("unused")
public class AlkaidMongodbTest {
    SyncMongodbConnection syncMongodbConnection = new AlkaidMongodb().database("test").sync();
    String testCollection = "testAlkaid";

    @Test
    public void insertTest() {
        syncMongodbConnection.create(testCollection,
                new Document("name", "Neko")
                        .append("age", 4)
        );
    }

    @Test
    public void insertListTest() {
        syncMongodbConnection.create(testCollection,
                List.of(new Document("name", "Ne")
                        , new Document("name", "Neko")
                        , new Document("name", "NekoCore")
                )
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
