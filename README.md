<h1 align="center">🌟 Alkaid</h1>
<h5 align="center">A Lightweight Kit Aim In Development.</h5>

## 🍀 这是什么？

这是一个工具库，封装 Bukkit BungeeCord 以及其他计划中的 Minecraft 服务端 API

通常的封装会将原先的 API 转换为 stream + lambda 以获得更高的效率

**来点简单的事件监听**

使用 Alkaid 提供的流式 API，可以省去原先使用 Bukkit API 定义监听器所需的继承再重写的繁杂步骤

```java
new AlkaidEvent(plugin).simple()
                 .use(event -> ((PlayerLoginEvent) event).getPlayer().sendMessage("欢迎"))
                 .with(PlayerLoginEvent.class)
                 .priority(EventPriority.HIGHEST)
                 .ignore(false)
                 .register();
```

**遇到特定事件停止监听**

```java
new AlkaidEvent(plugin).conditional()
                .use(event -> ((PlayerBedEnterEvent) event).getPlayer().sendMessage("晚安"))
                // 监听此事件
                .with(PlayerBedEnterEvent.class)
                // 监听到此事件时停止监听
                .interrupt(PlayerBedLeaveEvent.class)
                .ignore(true)
                .priority(EventPriority.HIGHEST)
                .register();
```

**注册指令**

```java
new AlkaidCommand(plugin).simple()
                .command("alkaid")
                .description("须臾曈昽开晓晴 烂银一色摇光晶")
                .permission("apj.20fans")
                .usage("/alkaid")
                .aliases(new ArrayList<>() {{
                    add("alias");
                }})
                .executor((sender, command, label, args) -> {
                    sender.sendMessage("你好！");
                    return true;
                })
                .tab((sender, command, alias, args) -> new ArrayList<>() {{
                    add("你好");
                }})
                .register();
```

**注册任务**

```java
new AlkaidTask(plugin).simple()
                .run(() -> System.out.println("快和我一起歌唱 好孩子才不怕悲伤"))
                .delay(20)
                .period(20)
                .async(true)
                .register();
```

## ✨ 模块

| 模块              | 描述                        | Bukkit 支持 | Bungee Cord 支持 |
| ----------------- | --------------------------- | ----------- | ---------------- |
| alkaid-bukkit     | Bukkit API 流式封装         | ✔️           | ❌                |
| alkaid-bungeecord | Bungee Cord 流式封装        | ❌           | ✔️                |
| alkaid-common     | 服务端无关工具类 如 sha256  | ✔️           | ✔️                |
| alkaid-log        | 控制台 Logger 封装 包括色彩 | ✔️           | ✔️                |
| alkaid-mongodb    | 对于 MongoDB 数据库的封装   | ✔️           | ✔️                |

