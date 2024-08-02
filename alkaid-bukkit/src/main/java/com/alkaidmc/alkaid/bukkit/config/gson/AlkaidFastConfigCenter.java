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

package com.alkaidmc.alkaid.bukkit.config.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> zh </p>
 * 基于Google Gson的通用快速配置中心 <br>
 *
 * <p> en </p>
 * Universal Fast Configuration Center based on Google Gson. <br>
 */
public class AlkaidFastConfigCenter {
    @Getter
    @Accessors(fluent = true)
    @SuppressWarnings("unused")
    @RequiredArgsConstructor(staticName = "of")
    public static class Pair<F, S> {
        final F first;
        final S second;
    }

    private final Gson gson = AlkaidGsonBuilder.gson();

    private final Map<String, Pair<File, JsonObject>> jsonFiles = new HashMap<>();

    /**
     * <p> zh </p>
     * 根据文件名读取配置对象
     *
     * <p> en </p>
     * Retrieves the configuration object for the given file name.
     *
     * @param fileName 文件名 / the name of the JSON file to retrieve the configuration object from
     * @return 配置对象 / the configuration object associated with the specified file name
     */
    private JsonObject getConfigObject(String fileName) {
        if (jsonFiles.containsKey(fileName)) {
            return jsonFiles.get(fileName).second();
        } else {
            File configFile = getConfigFile(fileName);
            JsonObject configObject;
            if (configFile.isFile()) {
                String jsonText = readFile(configFile);
                configObject = gson.fromJson(jsonText, JsonObject.class);
            } else {
                configObject = new JsonObject();
            }
            jsonFiles.put(fileName, Pair.of(configFile, configObject));
            return configObject;
        }
    }

    /**
     * <p> zh </p>
     * 设置默认配置
     *
     * <p> en </p>
     * Sets the default configuration for the specified file name.
     *
     * @param fileName 文件名 / the name of the file
     * @param def      配置的默认对象 / the default configuration object
     */
    public void makeDefaultConfig(String fileName, Object def) {
        JsonObject defaultObject = gson.fromJson(gson.toJson(def), JsonObject.class);
        JsonObject configObject = getConfigObject(fileName);

        defaultObject.keySet().forEach(it -> {
            if (!configObject.has(it)) {
                configObject.add(it, defaultObject.get(it));
            }
        });
        save(fileName);
    }

    /**
     * <p> zh </p>
     * 从配置中读取快照对象
     *
     * <p> en </p>
     * Reads a snapshot of the configuration from the specified file name.
     *
     * @param fileName 文件名 / the name of the file
     * @param type     配置的Class类型 / the class type of the configuration object
     * @param <T>      配置的类型 the type of the configuration object
     * @return 一个的快照对象 / a snapshot of the configuration
     */
    public <T> T readSnapshot(String fileName, Class<T> type) {
        return gson.fromJson(gson.toJson(getConfigObject(fileName)), type);
    }

    private File getConfigFile(String fileName) {
        return new File(fileName);
    }

    /**
     * <p> zh </p>
     * 根据键读取配置的值
     *
     * <p> en </p>
     * Retrieves the value associated with the specified key from the configuration file.
     *
     * @param fileName 文件名 / the name of the file
     * @param key      访问值的键 / the key of the value
     * @return 根据键读取配置的值如果值为空则返回新的Json对象 /
     * the value associated with the key, or a new JsonObject if the key is not found
     */
    public JsonElement getValue(String fileName, String key) {
        var element = getConfigObject(fileName).get(key);
        if (element == null) {
            element = new JsonObject();
        }
        return element;
    }

    /**
     * <p> zh </p>
     * 保存配置的值
     *
     * <p> en </p>
     * Sets the value associated with the specified key in the configuration file.
     *
     * @param fileName 文件名 / the name of the file
     * @param key      保存的键 / the key of the value
     * @param value    要存入的值 / the value to be set
     */
    public void setValue(String fileName, String key, JsonElement value) {
        getConfigObject(fileName).add(key, value);
        save(fileName);
    }

    /**
     * <p> zh </p>
     * 将给定对象转换为JsonPrimitive对象，支持的数据类型包括Boolean、Number、String和Character。
     * 如果传入的对象类型不在此范围内，将抛出IllegalArgumentException异常。
     *
     * <p> en </p>
     * Converts the given object to a JsonPrimitive instance, supporting types such as Boolean,
     * Number, String, and Character. An IllegalArgumentException is thrown if the object's type
     * does not fall within these supported categories.
     *
     * @param value 待转换的对象 / the object to be converted
     * @return 转换后的JsonPrimitive对象 / the resulting JsonPrimitive instance
     * @throws IllegalArgumentException 如果value的类型不受支持 / if the type of value is unsupported
     */
    public JsonPrimitive convertToJsonPrimitive(Object value) {
        if (value instanceof Boolean) {
            return new JsonPrimitive((Boolean) value);
        } else if (value instanceof Number) {
            return new JsonPrimitive((Number) value);
        } else if (value instanceof String) {
            return new JsonPrimitive((String) value);
        } else if (value instanceof Character) {
            return new JsonPrimitive((Character) value);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + value.getClass().getName());
        }
    }

    /**
     * <p> zh </p>
     * 保存所有配置文件
     *
     * <p> en </p>
     * Saves all the configuration files.
     */
    public void saveAll() {
        jsonFiles.keySet().forEach(this::save);
    }

    /**
     * <p> zh </p>
     * 保存配置文件
     *
     * <p> en </p>
     * Saves the configuration files.
     */
    private void save(String fileName) {
        if (jsonFiles.containsKey(fileName)) {
            File file = jsonFiles.get(fileName).first();
            JsonObject obj = jsonFiles.get(fileName).second();
            file.getParentFile().mkdirs();
            saveFile(file, gson.toJson(obj));
            System.out.println(obj);
        } else {
            System.out.println("保存失败：" + fileName);
        }
    }

    /**
     * <p> zh </p>
     * 保存文本到文本文件
     * <p> en </p>
     * Saves the text content to the specified file.
     *
     * @param file 要保存的文件 / the file to save
     * @param text 要保存的文本 / the text content to be saved
     */
    @SneakyThrows
    private void saveFile(File file, String text) {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
        fileOutputStream.close();
    }

    /**
     * <p> zh </p>
     * 将文本文件读取到字符串
     *
     * <p> en </p>
     * Reads the text content from the specified file.
     *
     * @param file 要读取的文件的文件名 / the file to read
     * @return 读取后的字符串 / the text content of the file
     */
    @SneakyThrows
    private String readFile(File file) {
        FileInputStream fileInputStream = new FileInputStream(file);
        String text = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
        fileInputStream.close();
        return text;
    }
}
