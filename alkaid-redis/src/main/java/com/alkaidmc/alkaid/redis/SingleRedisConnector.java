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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class SingleRedisConnector {
    String host = "127.0.0.1";
    int port = 6379;
    String auth = null;
    int connect = 8;
    int timeout = 1000;
    long sleep = 1000;
    JedisPoolConfig config = new JedisPoolConfig();

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    JedisPool pool;

    public SingleRedisConnector connect() {
        config.setMaxTotal(connect);

        pool = Optional.ofNullable(auth)
                .map(password -> new JedisPool(config, host, port, timeout, password))
                .orElseGet(() -> new JedisPool(config, host, port, timeout));

        return this;
    }

    public void disconnect() {
        pool.destroy();
    }

    public SingleRedisConnection connection() {
        return new SingleRedisConnection(pool, sleep);
    }
}
