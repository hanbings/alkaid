package com.alkaidmc.alkaid.loom.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class UsingAction extends DefaultTask {
    @TaskAction
    @SuppressWarnings("SpellCheckingInspection")
    public void using(String module) {
        // 解析使用的依赖
        switch (module) {
            case "bukkit": {
                System.out.println("Using Bukkit");
            }

            case "bungeecord": {
                System.out.println("Using Bungeecord");
            }

            default: break;
        }
    }
}
