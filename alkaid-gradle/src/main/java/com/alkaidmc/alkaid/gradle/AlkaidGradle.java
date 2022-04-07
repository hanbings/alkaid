package com.alkaidmc.alkaid.gradle;

import com.alkaidmc.alkaid.gradle.config.AlkaidGradleExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.bundling.Jar;

public class AlkaidGradle implements Plugin<Project> {

    @Override
    @SuppressWarnings("SpellCheckingInspection")
    public void apply(Project project) {
        // 添加配置
        AlkaidGradleExtension config = project.getExtensions().create("alkaid", AlkaidGradleExtension.class);
        // hook jar 任务
        project.getTasks().withType(Jar.class).configureEach(task -> {
            // 扫描代码 查找 Alkaid 调用 判断所需要应用的平台
            // 如 Alkaid.bukkit() Alkaid.bungeecord()
            // 添加一个文件到 Jar 中
            task.getSource().getFiles().add(null);
        });
    }
}
