package com.alkaidmc.alkaid.gradle.config;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Nested;

public abstract class AlkaidGradleExtension extends DefaultTask {
    @Nested
    abstract public AlkaidPluginConfig getAlkaidPlugin();

    @Nested
    abstract public AlkaidDependenciesConfig getAlkaidDependencies();

    public void alkaidPlugin(Action<? super AlkaidPluginConfig> action) {
        action.execute(getAlkaidPlugin());
    }

    public void alkaidDependencies(Action<? super AlkaidDependenciesConfig> action) {
        action.execute(getAlkaidDependencies());
    }
}
