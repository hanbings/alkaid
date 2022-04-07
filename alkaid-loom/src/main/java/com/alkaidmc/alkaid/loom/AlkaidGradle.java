package com.alkaidmc.alkaid.loom;

import com.alkaidmc.alkaid.loom.config.AlkaidGradleExtension;
import com.alkaidmc.alkaid.loom.task.AlkaidUsingAction;
import com.alkaidmc.alkaid.loom.type.AlkaidModuleType;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.bundling.Jar;

@SuppressWarnings("unused")
public class AlkaidGradle implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // 添加配置
        AlkaidGradleExtension config = project.getExtensions().create("alkaid", AlkaidGradleExtension.class);
        // 判空版本号
        if (config.getVersion().isPresent()) {
            throw new IllegalArgumentException("version is not null");
        }
        // 获取版本号
        String version = config.getVersion().get();

        // 基本依赖
        project.getDependencies().create(String.format("%s:%s:%s",
                AlkaidModuleType.CORE.getGroup(),
                AlkaidModuleType.CORE.getArtifact(),
                version));
        project.getDependencies().create(String.format("%s:%s:%s",
                AlkaidModuleType.API.getGroup(),
                AlkaidModuleType.API.getArtifact(),
                version));

        // 添加任务
        project.getTasks().register("using", AlkaidUsingAction.class);

        // hook jar 任务
        project.getTasks().withType(Jar.class).configureEach(task -> {
            // 添加一个文件到 Jar 中
            task.getSource().getFiles().add(null);
        });
    }
}
