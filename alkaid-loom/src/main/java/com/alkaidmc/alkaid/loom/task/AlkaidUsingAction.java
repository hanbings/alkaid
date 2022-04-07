package com.alkaidmc.alkaid.loom.task;

import com.alkaidmc.alkaid.loom.config.AlkaidGradleExtension;
import com.alkaidmc.alkaid.loom.type.AlkaidModuleType;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.util.Optional;

public class AlkaidUsingAction extends DefaultTask {

    @TaskAction
    public void using(String module) {
        // 获取版本号
        String version = ((AlkaidGradleExtension) this.getProject().getExtensions().getByName("alkaid"))
                .getVersion().get();

        // 解析使用的依赖
        Optional.ofNullable(AlkaidModuleType.lookup(module)).ifPresent(alkaidModuleType -> {
            // 添加依赖
            this.getProject().getDependencies()
                    .create(String.format("%s:%s:%s", alkaidModuleType.getGroup(), module, version));
        });
    }
}
