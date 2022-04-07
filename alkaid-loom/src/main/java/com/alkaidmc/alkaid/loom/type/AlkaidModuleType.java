package com.alkaidmc.alkaid.loom.type;

import lombok.AllArgsConstructor;

@SuppressWarnings("SpellCheckingInspection")
@AllArgsConstructor
public enum AlkaidModuleType {
    CORE("core", "com.alkaidmc.alkaid", "alkaid-core"),
    API("api", "com.alkaidmc.alkaid", "alkaid-api"),
    BUKKIT("bukkit", "com.alkaidmc.alkaid", "alkaid-bukkit"),
    BUNGEECORD("bungeecord", "com.alkaidmc.alkaid", "alkaid-bungee");

    // 加载器名称
    private final String module;
    // 依赖的 Alkaid 模块 依赖项
    private final String group;
    private final String artifact;

    public static AlkaidModuleType lookup(String module) {
        for (AlkaidModuleType type : AlkaidModuleType.values()) {
            if (type.getModule().equalsIgnoreCase(module)) {
                return type;
            }
        }
        return null;
    }

    public String getModule() {
        return module;
    }

    public String getGroup() {
        return group;
    }

    public String getArtifact() {
        return artifact;
    }
}
