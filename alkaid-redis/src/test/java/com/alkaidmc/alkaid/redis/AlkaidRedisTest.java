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

package com.alkaidmc.alkaid.redis;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class AlkaidRedisTest {
    @lombok.Data
    @AllArgsConstructor
    static class Data {
        String message;
        int number;
        boolean flag;
        HashMap<String, String> map;
        String[] array;
    }

    @Test
    public void test() {
        RedisConnector redis = new AlkaidRedis().redis();

        Data dataWrite = new Data("点一份炒饭",
                1919810,
                true,
                new HashMap<>() {{
                    put("锟斤拷", "烫烫烫");
                }},
                new String[]{"1", "1", "4", "5", "1", "4"});

        // 写入数据
        redis.setObj("test", dataWrite);
        // 读取数据
        Data dataRead = (Data) redis.getObj("test", Data.class);
        // 测试数据
        assertNotNull(dataRead);
        assertEquals(dataWrite, dataRead);
        // 删除数据
        redis.del("test");
        assertNull(redis.get("test"));
    }
}
