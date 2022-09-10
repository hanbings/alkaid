package com.alkaidmc.alkaid.bukkit.extra;

import com.alkaidmc.alkaid.bukkit.event.AlkaidEvent;
import com.alkaidmc.alkaid.bukkit.extra.event.SecondEvent;
import com.alkaidmc.alkaid.bukkit.extra.event.TickEvent;
import com.alkaidmc.alkaid.bukkit.task.AlkaidTask;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unused")
public class AlkaidExtra {
    final Plugin plugin;

    public AlkaidExtra(Plugin plugin) {
        this.plugin = plugin;

        // 监听插件关闭

    }

    public void tick() {
        // 检测是否已经有同类事件已经被加载
        if (loaded("TickEvent")) {
            return;
        }

        AtomicLong tick = new AtomicLong(0);

        // 每 tick 执行一次
        new AlkaidTask(plugin).simple()
                .delay(0)
                .period(1)
                .run(() -> Bukkit.getPluginManager().callEvent(new TickEvent(tick.incrementAndGet())))
                .register();
    }

    public void second() {
        // 检测是否已经有同类事件已经被加载
        if (loaded("SecondEvent")) {
            return;
        }

        // 创建调度器
        Timer timer = new Timer();
        // 每秒执行一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new SecondEvent(System.currentTimeMillis()));
            }
        }, 0, 1000);

        // 监听插件关闭事件
        new AlkaidEvent(plugin).predicate()
                .event(PluginDisableEvent.class)
                .priority(EventPriority.HIGHEST)
                .filter(e -> e.getPlugin().equals(plugin))
                .listener(e -> timer.cancel())
                .register();
    }

    public <T> boolean loaded(String clazz) {
        try {
            // 反射获取类 JavaPluginLoader
            Class<?> jcl = Bukkit.class
                    .getClassLoader()
                    .loadClass("org.bukkit.plugin.java.JavaPluginLoader");

            // 反射获取类 PluginClassLoader
            @SuppressWarnings("all")
            List pcls = (List) jcl.getField("loaders").get(List.class);

            for (Object pcl : pcls) {
                // 获取加载器所加载的类文件
                @SuppressWarnings("all")
                Map classes = (Map) pcl.getClass().getField("classes").get(Map.class);

                // 任意加载器加载了类即可返回 true
                if (classes.containsKey(clazz)) return true;
            }
        } catch (ClassNotFoundException |
                 NoSuchFieldException |
                 IllegalAccessException ignored) {
        }

        return false;
    }
}
