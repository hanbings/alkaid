package com.alkaidmc.alkaid.redis;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SuppressWarnings("unused")
public class AlkaidRedisTest {
    @Test
    public void single() {
        SingleRedisConnection connection = new AlkaidRedis().single()
                .host("127.0.0.1")
                .port(6379)
                .connect()
                .connection();

        // 写入数据
        IntStream.range(114514, 114517).forEach(count -> {
            connection.set(String.valueOf(count), "191810");
        });
        // 读取数据
        String data = connection.get("114514");
        assertEquals("191810", data);

        // 删除数据
        connection.del("114514");
        // 读取数据
        data = connection.get("114514");
        assertNull(data);

        // 定时删除数据
        connection.set("114514", "191810");
        connection.expire("114514", 3);
        // 读取数据
        data = connection.get("114514");
        assertEquals("191810", data);

        // 等待五秒
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 读取数据
        data = connection.get("114514");
        assertNull(data);

        // 订阅消息
        connection.subscribe("alkaid", (message) -> {
            assertEquals("test", message);
        });
        // 发布消息
        connection.publish("alkaid", "test");

        // 等待五秒
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
