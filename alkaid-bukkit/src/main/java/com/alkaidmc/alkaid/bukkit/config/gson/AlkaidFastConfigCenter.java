package com.alkaidmc.alkaid.bukkit.config.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AlkaidFastConfigCenter {
    @Getter
    @Accessors(fluent = true)
    @SuppressWarnings("unused")
    @RequiredArgsConstructor(staticName = "of")
    public static class Pair<F, S> {
        final F first;
        final S second;
    }

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, Pair<File, JsonObject>> jsonFiles = new HashMap<>();

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

    public <T> T readSnapshot(String fileName, Class<T> type) {
        return gson.fromJson(gson.toJson(getConfigObject(fileName)), type);
    }

    private File getConfigFile(String fileName) {
        return new File(fileName);
    }

    public JsonElement getValue(String fileName, String key) {
        var element = getConfigObject(fileName).get(key);
        if (element == null) {
            element = new JsonObject();
        }
        return element;
    }

    public void setValue(String fileName, String key, JsonElement value) {
        getConfigObject(fileName).add(key, value);
        save(fileName);
    }

    public void saveAll() {
        jsonFiles.keySet().forEach(this::save);
    }

    private void save(String fileName) {
        if (jsonFiles.containsKey(fileName)) {
            File file = jsonFiles.get(fileName).first();
            JsonObject obj = jsonFiles.get(fileName).second();
            file.getParentFile().mkdirs();
            saveFile(file, gson.toJson(obj));
            System.out.println(obj);
        } else {
            System.out.println("保存失败：$fileName");
        }
    }

    @SneakyThrows
    private void saveFile(File file, String text) {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
        fileOutputStream.close();
    }

    @SneakyThrows
    private String readFile(File file) {
        FileInputStream fileInputStream = new FileInputStream(file);
        String text = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);
        fileInputStream.close();
        return text;
    }
}
