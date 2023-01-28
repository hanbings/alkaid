/*
 * Copyright 2023 Alkaid
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

package com.alkaidmc.alkaid.bungeecord.task;

import com.alkaidmc.alkaid.bungeecord.task.interfaces.AlkaidTaskRegister;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.concurrent.TimeUnit;

/**
 * 请注意： Bungee cord 的任务调度是全异步的
 * 默认的时间单位是秒 如果有需要可以通过 unit 传入 TimeUnit 更改时间单位
 */
@RequiredArgsConstructor
public class SimpleTaskRegister implements AlkaidTaskRegister {
    final Plugin plugin;

    // 在任务执行前等待的时间
    long delay = 0;
    // 重复执行任务之间的时间间隔
    long period = 0;
    // 时间单位
    TimeUnit unit = TimeUnit.SECONDS;

    // 任务 参数为已执行次数
    Runnable run;
    // 任务实例
    @Setter(AccessLevel.NONE)
    ScheduledTask task;

    @Override
    public void register() {
        task = plugin.getProxy().getScheduler().schedule(plugin, run, delay, period, unit);
    }

    @Override
    public void unregister() {
        task.cancel();
    }
}
