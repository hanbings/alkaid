package com.alkaidmc.alkaid.loom.type;

import lombok.AllArgsConstructor;

@SuppressWarnings("SpellCheckingInspection")
@AllArgsConstructor
public enum MinecraftPluginType {
    BUKKIT(AlkaidModuleType.BUKKIT, "plugin.yml"),
    BUNGEECORD(AlkaidModuleType.BUNGEECORD, "bungee.yml");

    // 模块
    private final AlkaidModuleType type;
    // 配置文件
    private final String config;

    public static MinecraftPluginType lookup(AlkaidModuleType type) {
        for (MinecraftPluginType value : values()) {
            if (value.getType() == type) {
                return value;
            }
        }
        return null;
    }

    public String getConfig() {
        return config;
    }

    public AlkaidModuleType getType() {
        return type;
    }
}
