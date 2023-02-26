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

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("unused")
public class AlkaidMongodbTest {
    @Test
    @Deprecated
    public void sync() throws ExecutionException, InterruptedException {
        MongodbConnection connection = new AlkaidMongodb().mongodb()
                .host("localhost")
                .port(27017)
                .database("alkaid")
                .connect()
                .connection();

        // 清空数据
        connection.delete("test", new HashMap<>());
        // 写入数据
        connection.create("test",
                new Data("点一份炒饭",
                        1919810,
                        true,
                        new HashMap<>() {{
                            put("锟斤拷", "烫烫烫");
                        }},
                        new String[]{"1", "1", "4", "5", "1", "4"})
        );
        // 读取数据
        List<Data> data = connection.read("test",
                new HashMap<>() {{
                    put("message", "点一份炒饭");
                }},
                Data.class).get();
        // 测试数据
        assertEquals("点一份炒饭", data.get(0).message);
        assertEquals(1919810, data.get(0).number);
        assertTrue(data.get(0).flag);
        assertEquals("烫烫烫", data.get(0).map.get("锟斤拷"));
        assertEquals("1", data.get(0).array[0]);

        // 删除数据
        connection.delete("test",
                new HashMap<>() {{
                    put("message", "点一份炒饭");
                }});
        // 验证删除
        data = connection.read("test",
                new HashMap<>() {{
                    put("message", "点一份炒饭");
                }}, Data.class).get();
        assertEquals(0, data.size());

        // search 方法查询数据
        IntStream.range(114514, 114517).forEach(count -> {
            connection.create("test",
                    new Data("点一份炒饭",
                            count,
                            true,
                            new HashMap<>() {{
                                put("锟斤拷", "烫烫烫");
                            }}, new String[]{"1", "1", "4", "5", "1", "4"})
            );
        });

        // 查询数据
        data = connection.search("test", "number",
                114514, 114516, 10,
                Data.class).get();
        // 测试数据
        assertEquals(3, data.size());
    }

    @AllArgsConstructor
    static class Data {
        String message;
        int number;
        boolean flag;
        HashMap<String, String> map;
        String[] array;
    }
}
