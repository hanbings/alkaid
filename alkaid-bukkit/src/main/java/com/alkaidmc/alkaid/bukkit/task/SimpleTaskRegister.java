/*
 * Copyright 2022 Alkaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alkaidmc.alkaid.bukkit.task;

import com.alkaidmc.alkaid.bukkit.task.interfaces.AlkaidTaskRegister;
import com.alkaidmc.alkaid.bukkit.task.interfaces.AlkaidTaskRunnable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * <p> zh </p>
 * 简单任务注册器 <br>
 * <p> en </p>
 * The simple task register. <br>
 */
@Setter
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Accessors(fluent = true, chain = true)
public class SimpleTaskRegister implements AlkaidTaskRegister {
    final Plugin plugin;

    // 在任务执行前等待的时间 / tick
    long delay = 0;
    // 重复执行任务之间的时间间隔/tick
    long period = 0;
    // 是否异步
    boolean async = false;
    // 任务 参数为已执行次数
    AlkaidTaskRunnable run;

    // 执行器实例
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    BukkitRunnable runnable;
    // 任务实例
    @Setter(AccessLevel.NONE)
    BukkitTask task;

    @Override
    public void register() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                run.run();
            }
        };

        if (period <= 0) {
            task = async
                    ? runnable.runTaskLaterAsynchronously(plugin, delay)
                    : runnable.runTaskLater(plugin, delay);
        } else {
            task = async
                    ? runnable.runTaskTimerAsynchronously(plugin, delay, period)
                    : runnable.runTaskTimer(plugin, delay, period);
        }

    }

    @Override
    public void unregister() {
        runnable.cancel();
    }
}
