/*
 * Copyright 2023 Alkaid
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

package com.alkaidmc.alkaid.mongodb;

import com.alkaidmc.alkaid.mongodb.interfaces.Serialization;
import com.google.gson.Gson;
import org.bson.Document;

import java.util.Map;

public class GsonSerialization implements Serialization {
    final Gson gson;

    public GsonSerialization() {
        this.gson = new Gson();
    }

    public GsonSerialization(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> String serialize(T data) {
        return gson.toJson(data);
    }

    @Override
    public <K, V> String map(Map<K, V> data) {
        return gson.toJsonTree(data, Map.class).toString();
    }

    @Override
    public <T> T deserialize(Class<T> type, Document document) {
        return gson.fromJson(document.toJson(), type);
    }
}
