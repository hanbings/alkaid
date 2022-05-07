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
import redis.clients.jedis.JedisPool;

import java.util.Optional;

@Setter
@Getter
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class AlkaidRedis {
    String host = "localhost";
    int port = 6379;
    String user;
    String password;

    private JedisPool jedisPool = new JedisPool();
    int maxTotal = 100;
    int maxIdle = 10;
    int minIdle = 0;
    int maxWait = -1;
    boolean testOnBorrow = false;
    boolean testOnReturn = false;
    boolean testWhileIdle = false;
    int timeBetweenEvictionRunsMillis = -1;
    int numThreads = -1;
    int minEvictableIdleTimeMillis = -1;
    boolean softMinEvictableIdleTimeMillis = false;
    boolean lifo = false;
    boolean fairness = false;
    boolean jmxEnabled = false;


    // redis 链式配置文件
    // https://github.com/AlkaidMC/alkaid/projects/1#card-81556630
    public RedisConnector redis() {
        jedisPool.setMaxTotal(maxTotal);
        jedisPool.setMaxIdle(maxIdle);
        jedisPool.setMinIdle(minIdle);
        jedisPool.setMaxWaitMillis(maxWait);
        jedisPool.setTestOnBorrow(testOnBorrow);
        jedisPool.setTestOnReturn(testOnReturn);
        jedisPool.setTestWhileIdle(testWhileIdle);
        jedisPool.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        jedisPool.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        jedisPool.setLifo(lifo);
        Jedis resource = jedisPool.getResource();

        if (user != null && password != null)
            resource.auth(user, password);
        else if (user == null && password != null)
            resource.auth(password);
        return new RedisConnector(resource);
    }
}
