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
import lombok.NoArgsConstructor;

@SuppressWarnings("unused")
@NoArgsConstructor
public class AlkaidRedis {
    @Getter
    String host = "localhost";
    @Getter
    int port = 6379;
    @Getter
    String auth = null;

    public AlkaidRedis host(String host) {
        this.host = host;
        return this;
    }

    public AlkaidRedis port(int port) {
        this.port = port;
        return this;
    }

    public RedisConnector redis() {
        return new RedisConnector(host, port);
    }
}
