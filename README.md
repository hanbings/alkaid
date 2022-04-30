![Alkaid](https://picture.hanbings.com/2022/05/01/d321c9d50fb71.png)

<h1 align="center">🌟 Alkaid</h1>
<h5 align="center">A Lightweight Kit Aim In Development.</h5>

## 🍀 这是什么？

这是一个工具库，封装 Bukkit BungeeCord 以及其他计划中的 Minecraft 服务端 API

通常的封装会将原先的 API 转换为 stream + lambda 以获得更高的效率

**来点简单的事件监听**

使用 Alkaid 提供的流式 API，可以省去原先使用 Bukkit API 定义监听器所需的继承再重写的繁杂步骤

```java
new AlkaidEvent(plugin).simple()
                .listener(event -> {
                    ((PlayerLoginEvent) event).getPlayer().sendMessage("欢迎");
                })
                .event(PlayerLoginEvent.class)
                .priority(EventPriority.HIGHEST)
                .ignore(false)
                .register();
```

**遇到特定事件停止监听**

```java
new AlkaidEvent(plugin).conditional()
                .listener(event -> {
                    ((PlayerBedEnterEvent) event).getPlayer().sendMessage("晚安");
                })
                // 监听此事件
                .event(PlayerBedEnterEvent.class)
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

**创建一本书**

```java
new AlkaidInventory(plugin).book()
                .title("这是一本书")
                .author("这是一本书的作者")
                .write("这是往书里写了一句话")
                .write(2, "这是往第三页写了一句话")
                // 生成书的 ItemStack
                .written();
```

**创建自定义箱子界面**

```java
new AlkaidInventory(plugin).gui()
                // 大小
                .rows(6)
                // 持有者
                .holder(Bukkit.getPlayer("hanbings"))
                // 不允许拖拽
                .drag(false)
                // 标题
                .title("Alkaid")
                // 设置特定位置的物品
                .item(new ItemStack(Material.BOOK), 12, 13, 14)
                .item(new ItemStack(Material.LIGHT_BLUE_BANNER), 32, 33, 34)
                // 设置空闲位置的物品
                .free(new ItemStack(Material.BLACK_BANNER))
                // 设置物品的打开事件
                .open((e) -> e.getPlayer().sendMessage("打开了"))
                // 设置物品的点击事件
                .click((e) -> e.getWhoClicked().sendMessage("点了一下"), 1, 2, 3)
                .click((e) -> e.getWhoClicked().sendMessage("点了一下"), 4, 5, 6)
                // 设置物品的关闭事件
                .close((e) -> e.getPlayer().sendMessage("关闭了"))
                .create();
```

**辅助反射**

```java
new AlkaidCommon().reflection()
                // 设置加载器
                .loader(AlkaidCommon.class.getClassLoader())
                // 设置类名 第二个参数为异常处理 可选
                .load("com.alkaidmc.alkaid.common.AlkaidCommon", Throwable::printStackTrace)
                .load("com.alkaidmc.alkaid.common.lang.ClassSwitch")
                // 查找方法 第二个参数为异常处理 可选
                .method("test", String.class, String.class)
                .method("test", Throwable::printStackTrace, String.class)
                // 默认异常处理
                .error(Throwable::printStackTrace)
                // 执行方法
                .invoke("test", "Alkaid", "Common");
```

## ✨ 模块

| 模块              | 描述                        | Bukkit 支持 | Bungee Cord 支持 |
| ----------------- | --------------------------- | ----------- | ---------------- |
| alkaid-bukkit     | Bukkit API 流式封装         | ✔️           | ❌                |
| alkaid-bungeecord | Bungee Cord 流式封装        | ❌           | ✔️                |
| alkaid-common     | 服务端无关工具类 如 sha256  | ✔️           | ✔️                |
| alkaid-log        | 控制台 Logger 封装 包括色彩 | ✔️           | ✔️                |
| alkaid-mongodb    | 对于 MongoDB 数据库的封装   | ✔️           | ✔️                |

## 🔨 怎么使用？

目前 Alkaid Lib 发布在 https://repository.alkaidmc.com

需要通过 Maven 或 Gradle 添加自定义仓库再添加对应模块依赖

**Maven**

```xml
<repository>
  <id>alkaidmc-repository-releases</id>
  <name>AlkaidMC Repository</name>
  <url>https://repository.alkaidmc.com/releases</url>
</repository>

<repository>
  <id>alkaidmc-repository-snapshots</id>
  <name>AlkaidMC Repository</name>
  <url>https://repository.alkaidmc.com/snapshots</url>
</repository>
```

**Gradle**

```groovy
maven {
    url "https://repository.alkaidmc.com/releases"
    url "https://repository.alkaidmc.com/snapshots"
}
```

**Gradle Kotlin**

```kotlin
maven {
    url = uri("https://repository.alkaidmc.com/releases")
    url = uri("https://repository.alkaidmc.com/snapshots")
}
```

