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

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class AlkaidRedis {
    String host = "localhost";
    int port = 6379;
    String auth = null;

    public RedisConnector redis() {
        Jedis jedis = new Jedis(host, port);
        Optional.ofNullable(auth).ifPresent(jedis::auth);
        return new RedisConnector(jedis);
    }
}
