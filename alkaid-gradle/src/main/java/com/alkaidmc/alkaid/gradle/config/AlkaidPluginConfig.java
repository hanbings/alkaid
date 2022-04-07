package com.alkaidmc.alkaid.gradle.config;

import org.gradle.api.provider.Property;

import java.util.List;

/**
 * 提供 Alkaid 插件的配置信息
 */
public abstract class AlkaidPluginConfig {
    // 插件作者
    abstract public Property<String> getAuthor();
    // 插件版本
    abstract public Property<String> getVersion();
    // 插件名称
    abstract public Property<String> getName();
    // 插件介绍
    abstract public Property<String> getDescription();
    // 插件网页
    abstract public Property<String> getWebsite();
    // 插件硬依赖
    abstract public Property<List<String>> getDepend();
    // 插件软依赖
    abstract public Property<List<String>> getSoftDepend();
}
