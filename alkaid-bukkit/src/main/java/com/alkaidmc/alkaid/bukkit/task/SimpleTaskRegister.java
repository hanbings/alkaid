package com.alkaidmc.alkaid.bukkit.task;

import com.alkaidmc.alkaid.bukkit.task.interfaces.AlkaidTaskRegister;
import com.alkaidmc.alkaid.bukkit.task.interfaces.AlkaidTaskRunnable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SimpleTaskRegister implements AlkaidTaskRegister {
    final JavaPlugin plugin;

    // 在任务执行前等待的时间 / tick
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    long delay = 0;
    // 重复执行任务之间的时间间隔/tick
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    long period = 0;
    // 是否异步
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    boolean async = false;
    // 任务 参数为已执行次数
    @Setter
    @Getter
    @Accessors(fluent = true, chain = true)
    AlkaidTaskRunnable run;

    // 执行器实例
    BukkitRunnable runnable;
    // 任务实例
    @Getter
    @Accessors(fluent = true)
    BukkitTask task;

    @Override
    public void register() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                run.run();
            }
        };
        task = async
                ? runnable.runTaskTimerAsynchronously(plugin, delay, period)
                : runnable.runTaskTimer(plugin, delay, period);
    }

    @Override
    public void unregister() {
        runnable.cancel();
    }
}
