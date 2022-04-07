package com.alkaidmc.alkaid.loom;

import com.alkaidmc.alkaid.loom.config.AlkaidGradleExtension;
import com.alkaidmc.alkaid.loom.task.AlkaidUsingAction;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.bundling.Jar;

public class AlkaidGradle implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // 添加配置
        AlkaidGradleExtension config = project.getExtensions().create("alkaid", AlkaidGradleExtension.class);
        // 添加任务
        project.getTasks().register("using", AlkaidUsingAction.class);
        // hook jar 任务
        project.getTasks().withType(Jar.class).configureEach(task -> {
            // 添加一个文件到 Jar 中
            task.getSource().getFiles().add(null);
        });
    }
}
