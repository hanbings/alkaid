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
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SingleRedisConnection {
    final static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    final JedisPool pool;
    final long sleep;
    // 监听控制器
    boolean listening = false;

    public void set(String key, String value) {
        pool.getResource().set(key, value);
        pool.close();
    }

    public String get(String key) {
        String value = pool.getResource().get(key);
        pool.close();
        return value;
    }

    public void del(String key) {
        pool.getResource().del(key);
        pool.close();
    }

    public void expire(String key, int seconds) {
        pool.getResource().expire(key, seconds);
        pool.close();
    }

    public boolean exists(String key) {
        boolean exists = pool.getResource().exists(key);
        pool.close();
        return exists;
    }

    public void publish(String channel, String message) {
        pool.getResource().publish(channel, message);
        pool.close();
    }

    public void subscribe(String channel, Consumer<String> message) {
        scheduler.schedule(() -> {
            if (listening) {
                pool.getResource().subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String ch, String msg) {
                        message.accept(msg);
                    }
                }, channel);
            }
        }, sleep, TimeUnit.MILLISECONDS);
    }

    public void unsubscribe() {
        this.listening = false;
    }
}
