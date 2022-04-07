package com.alkaidmc.alkaid.gradle.config;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Nested;

public abstract class AlkaidGradleExtension extends DefaultTask {
    @Nested
    abstract public AlkaidPluginConfig getAlkaidPlugin();

    public void alkaidPlugin(Action<? super AlkaidPluginConfig> action) {
        action.execute(getAlkaidPlugin());
    }
}
