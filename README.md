<h1 align="center">🌟 Alkaid</h1>
<h5 align="center">A Lightweight Kit Aim In Development.</h5>

# 这是什么？

这是一个工具库，封装 Bukkit BungeeCord 以及其他计划中的 Minecraft 服务端 API

通常的封装会将原先的 API 转换为 stream + lambda 以获得更高的效率

如 alkaid-bukkit 中的 event 封装

使用 Alkaid 提供的流式 API，可以省去原先使用 Bukkit  API 定义监听器所需的继承再重写的繁杂步骤

```java
Alkaid.event.simple()
                 .use(event -> ((PlayerLoginEvent) event).getPlayer().sendMessage("欢迎使用 Alkaid"))
                 .listener(PlayerLoginEvent.class)
                 .priority(EventPriority.HIGHEST)
                 .ignore(false)
                 .register();
```
