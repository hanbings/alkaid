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

public class AlkaidRedisTest {
    RedisConnector redis = new AlkaidRedis().redis();

    public void setTest() {
        redis.set("test", "neko");
        System.out.println(redis.get("test"));
    }

    public void delTest() {
        setTest();
        System.out.println("exists test:" + redis.exists("test"));
        redis.del("test");
        System.out.println("exists test:" + redis.exists("test"));
        System.out.println(redis.get("test"));
    }
}
