package com.alkaidmc.alkaid.bukkit.config;

import com.alkaidmc.alkaid.bukkit.log.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

@AllArgsConstructor
public class JsonConfiguration {
    Logger logger;
    JavaPlugin plugin;
    Gson gson;

    @Builder
    public JsonConfiguration(JavaPlugin plugin, Logger logger, Gson gson) {
        // 空检查
        this.plugin = Optional
                .ofNullable(plugin)
                .orElseThrow(() -> new NullPointerException("arg plugin was null"));
        this.logger = Optional
                .ofNullable(logger)
                .orElseThrow(() -> new NullPointerException("arg logger was null"));
        this.gson = Optional
                .ofNullable(gson)
                .orElse(new GsonBuilder()
                        .enableComplexMapKeySerialization()
                        .serializeNulls()
                        .setPrettyPrinting()
                        .create());
    }
}
