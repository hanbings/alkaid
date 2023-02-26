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
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Deprecated
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SingleRedisConnection {
    final ExecutorService executor = Executors.newCachedThreadPool();
    final JedisPool pool;

    public void set(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, value);
        }
    }

    public String get(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    public void del(String key) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(key);
        }
    }

    public void expire(String key, int seconds) {
        try (Jedis jedis = pool.getResource()) {
            jedis.expire(key, seconds);
        }
    }

    public boolean exists(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.exists(key);
        }
    }

    public void publish(String channel, String message) {
        try (Jedis jedis = pool.getResource()) {
            jedis.publish(channel, message);
        }
    }

    public JedisPubSub subscribe(String channel, Consumer<String> message) {
        AlkaidPubSub sub = new AlkaidPubSub(message);

        executor.submit(() -> {
            try (Jedis jedis = pool.getResource()) {
                jedis.subscribe(sub, channel);
            } catch (Throwable x) {
                x.printStackTrace();
            }
        });

        return sub;
    }

    public JedisPubSub subscribe(String channel, Consumer<String> message, Consumer<Throwable> error) {
        AlkaidPubSub sub = new AlkaidPubSub(message);

        executor.submit(() -> {
            try (Jedis jedis = pool.getResource()) {
                jedis.subscribe(sub, channel);
            } catch (Throwable x) {
                error.accept(x);
            }
        });

        return sub;
    }

    public JedisPubSub subscribe(JedisPubSub sub, String channel) {
        executor.submit(() -> {
            try (Jedis jedis = pool.getResource()) {
                jedis.subscribe(sub, channel);
            } catch (Throwable x) {
                x.printStackTrace();
            }
        });

        return sub;
    }

    public JedisPubSub subscribe(JedisPubSub sub, String channel, Consumer<Throwable> error) {
        executor.submit(() -> {
            try (Jedis jedis = pool.getResource()) {
                jedis.subscribe(sub, channel);
            } catch (Throwable x) {
                error.accept(x);
            }
        });


        return sub;
    }

    static class AlkaidPubSub extends JedisPubSub {
        Consumer<String> consumer;

        public AlkaidPubSub(Consumer<String> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onMessage(String channel, String message) {
            consumer.accept(message);
        }
    }
}
