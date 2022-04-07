package com.alkaidmc.alkaid.interfaces;

public interface PluginBootloader {
    PluginBootloader loading();

    PluginBootloader enable();

    PluginBootloader disable();

    PluginBootloader reload();

    void plugin();
}
