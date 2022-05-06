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

import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class RedisConnector {

    final Jedis jedis;

    public RedisConnector set(String key, String value) {
        jedis.set(key, value);
        return this;
    }

    public RedisConnector set(String key, String value, int seconds) {
        jedis.setex(key, seconds, value);
        return this;
    }

    public RedisConnector expire(String key, int seconds) {
        jedis.expire(key, seconds);
        return this;
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public RedisConnector del(String key) {
        jedis.del(key);
        return this;
    }

    public boolean exists(String key) {
        return jedis.exists(key);
    }

    public void close() {
        jedis.close();
    }

}
