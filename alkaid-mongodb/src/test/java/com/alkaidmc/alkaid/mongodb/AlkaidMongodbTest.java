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

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("unused")
public class AlkaidMongodbTest {
    @AllArgsConstructor
    static class Data {
        String message;
        int number;
        boolean flag;
        HashMap<String, String> map;
        String[] array;
    }

    @Test
    public void connect() {
        SyncMongodbConnection connection = new AlkaidMongodb()
                .host("localhost")
                .port(2701)
                .database("alkaid")
                .sync();

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
                Data.class);
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
                }}, Data.class);
        assertEquals(0, data.size());

        // search 方法查询数据
        connection.create("test",
                new Data("点一份炒饭",
                        114514,
                        true,
                        new HashMap<>() {{
                            put("锟斤拷", "烫烫烫");
                        }}, new String[]{"1", "1", "4", "5", "1", "4"})
        );
        connection.create("test",
                new Data("点一份炒饭",
                        114515,
                        true,
                        new HashMap<>() {{
                            put("锟斤拷", "烫烫烫");
                        }}, new String[]{"1", "1", "4", "5", "1", "4"})
        );
        connection.create("test",
                new Data("点一份炒饭",
                        114516,
                        true,
                        new HashMap<>() {{
                            put("锟斤拷", "烫烫烫");
                        }}, new String[]{"1", "1", "4", "5", "1", "4"})
        );
        // 查询数据
        data = connection.search("test", "number", 114514, 114516, 10, Data.class);
        // 测试数据
        assertEquals(3, data.size());

        System.out.println(Arrays.toString(data.toArray()));
    }
}
