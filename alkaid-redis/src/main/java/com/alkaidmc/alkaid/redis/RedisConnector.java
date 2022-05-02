package com.alkaidmc.alkaid.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@NoArgsConstructor
@SuppressWarnings("unused")
public class RedisConnector {
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String host;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    int port;
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    String auth = null;

    public void connection() {
        Jedis jedis = new Jedis(host, port);
        Optional.ofNullable(auth).ifPresent(jedis::auth);
    }
}
