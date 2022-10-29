<h1 align="center">Alkaid Bukkit 模块</h1>
<h6 align="center">We also provide documentation in English. <a href="../#/">Click here to go</a></h6>

## 使用模块

引入 Bukkit 模块（注意替换版本号）

**Maven**

```xml
<dependency>
  <groupId>com.alkaidmc.alkaid</groupId>
  <artifactId>alkaid-bukkit</artifactId>
  <version>{{alkaid.version}}</version>
</dependency>
```

**Gradle**

```groovy
implementation "com.alkaidmc.alkaid:alkaid-bukkit:{{alkaid.version}}"
```

**Gradle Kotlin**

```kotlin
implementation("com.alkaidmc.alkaid:alkaid-bukkit:{{alkaid.version}}")
```

**创建模块引导类**

Bukkit 模块包含六个分类，它们分别有各自的模块引导类

1. 指令 Command

   ```java
   new AlkaidCommand(Plugin plugin);
   ```

2. 配置文件与序列化 Config

   ```java
   new AlkaidJsonConfiguration();
   ```

3. 事件 Event

   ```java
   new AlkaidEvent(Plugin plugin);
   ```

4. 扩展 Extra

   ```java
   new AlkaidExtra(Plugin plugin);
   ```

5. 服务端、依赖与 NMS Server

   ```java
   new AlkaidServer(Plugin plugin);
   ```

6. 任务 Task

   ```java
   new AlkaidTask(Plugin plugin);
   ```

## 指令 Command

| 入口类方法 | 返回值类型                                                   | 功能               |
| ---------- | ------------------------------------------------------------ | ------------------ |
| simple()   | [SimpleCommandRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/command/SimpleCommandRegister.java) | 简单指令注册器     |
| regex()    | [RegexCommandRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/command/RegexCommandRegister.java) | 正则匹配指令注册器 |
| parse()    | [ParseCommandRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/command/ParseCommandRegister.java) | 解析树指令注册器   |

## 配置文件与序列化 Config

### AlkaidJsonConfiguration

通过 AlkaidJsonConfiguration 从插件文件中释放默认 json 配置文件：

```java
public class Test extends JavaPlugin {
    @Data
    static class TestConfig {
        String test;
    }
    @Override
    public void onEnable() {
        TestConfig config = new AlkaidJsonConfiguration().load(
                this,
                new Gson(),
                "config.json",
                "config.json",
                TestConfig.class
        );
    }
}
```

对应的 config.json 文件放置于 resources 根目录下：

```
{"test": "test"}
```

其中的 `<T> T load(Plugin plugin, Gson gson, String resource, String path, Class<T> type)` 方法参数分别为 ：

`plugin` 存放 json 资源的插件实例

`gson` 用于解析 json 的 Gson 实例，可以使用 AlkaidGsonBuilder 获取带有序列化适配器的 Gson 实例

`resource` 存放在 resources 目录下的位置

`path` 为保存文件的位置，相对于 plugin 实例的 `getDataFolder()` 目录

`type` 为需要将 json 映射到的数据实体类 （POJO）

**AlkaidJsonConfiguration 不会自动创建缺失的文件夹，请先确保数据文件的上级目录存在**

### AlkaidGsonBuilder

为了使 Gson 能解析 Bukkit 的对象，Alkaid 提供了三个适配器，分别是 [ItemStackGsonAdapter](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/config/gson/ItemStackGsonAdapter.java)、[LocationGsonAdapter](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/config/gson/LocationGsonAdapter.java) 和 [PlayerGsonAdapter](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/config/gson/PlayerGsonAdapter.java) 它们分别对应 [ItemStack](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/ItemStack.html) [Location](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Location.html) 和 [Player](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/Player.html) 几个类型。

使用位于 `AlkaidGsonBuilder` 的静态方法 `gson()` 可以获得一个包含了以上三个适配器的 Gson 解析器。

这是它的实现方法：

```java
public static Gson gson() {
    return Optional.ofNullable(gson).orElseGet(() -> {
        gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(ItemStack.class, new ItemStackGsonAdapter())
                .registerTypeAdapter(Player.class, new PlayerGsonAdapter())
                .registerTypeAdapter(Location.class, new LocationGsonAdapter())
                .create();
        return gson;
    });
}
```

使用它，只需要一行：

```java
Gson gson = AlkaidGsonBuilder.gson();
```

## 事件 Event

| 入口类方法  | 返回值类型                                                   | 功能                 |
| ----------- | ------------------------------------------------------------ | -------------------- |
| simple()    | [SimpleEventFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/AlkaidEvent.java) | 简单的事件注册器     |
| predicate() | [PredicateEventFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/AlkaidEvent.java) | 谓词事件注册器       |
| section()   | [SectionEventFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/AlkaidEvent.java) | 事件段落注册器       |
| ~~count()~~ | ~~[CountEventFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/AlkaidEvent.java)~~ | ~~计数器事件注册器~~ |
| ~~chain()~~ | ~~[ChainEventRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/ChainEventRegister.java)~~ | ~~事件链注册器~~     |

### 简单事件注册器

#### 可配置变量

| 变量名   | 参数类型                       | 默认值               | 传入方法 | 功能                                                |
| -------- | ------------------------------ | -------------------- | -------- | --------------------------------------------------- |
| event    | org.bukkit.event.Event         | null                 | apply    | 指定所监听的事件                                    |
| ignore   | boolean                        | false                | apply    | 是否忽略其他事件取消该事件并继续处理该事件          |
| priority | org.bukkit.event.EventPriority | EventPriority.NORMAL | apply    | 事件的监听等级 LOWEST 为最先监听 HIGHEST 为最后监听 |
| listener | Consumer\<T\>                  | null                 | apply    | 对所监听的事件进行处理                              |

#### 完整示例

```java
new AlkaidEvent(this).simple()
    .event(PlayerJoinEvent.class)
    .ignore(false)
    .priority(EventPriority.NORMAL)
    .listener(e -> e.getPlayer().sendMessage("你好！"))
    .register();
```

### 谓词事件注册器

#### 可配置变量

| 变量名   | 参数类型                       | 默认值               | 传入方法 | 功能                                                         |
| -------- | ------------------------------ | -------------------- | -------- | ------------------------------------------------------------ |
| event    | org.bukkit.event.Event         | null                 | apply    | 指定所监听的事件                                             |
| ignore   | boolean                        | false                | apply    | 是否忽略其他事件取消该事件并继续处理该事件                   |
| priority | org.bukkit.event.EventPriority | EventPriority.NORMAL | apply    | 事件的监听等级 LOWEST 为最先监听 HIGHEST 为最后监听          |
| listener | Consumer\<T\>                  | null                 | apply    | 对所监听的事件进行处理                                       |
| filter   | Predicat\<T\>                  | null                 | append   | 可以多次设置过滤 与 Stream 类似 若 filter 语句返回 true 则继续执行下一个 filter 直至执行 listener 处理事件 |

#### 完整示例

```java
new AlkaidEvent(this).predicate()
    .event(AsyncPlayerChatEvent.class)
    .ignore(false)
    .priority(EventPriority.NORMAL)
    .filter(e -> e.getMessage().equals("test"))
    .listener(e -> e.getPlayer().sendMessage("你输入了 test"))
    .register();
```

使用 `filter()` 方法添加一段简短的过滤代码，代码返回的是 `false` 则会结束当前一次的事件处理器，反之，则会执行 `listener()` 方法预先设定好的处理流程。**请注意**，除非你的代码**真的非常简短**，如示例中判断一个 String 是否与预想的结果相同，否则，更好的做法是直接在 `listener()` 方法设置的处理流程中进行处理。

`filter()` 方法可以在同一个事件处理器多次使用。

### 事件段落注册器

#### 可配置变量

| 变量名    | 参数类型                         | 默认值               | 传入方法 | 功能                                                         |
| --------- | -------------------------------- | -------------------- | -------- | ------------------------------------------------------------ |
| event     | org.bukkit.event.Event           | null                 | apply    | 指定所监听的事件                                             |
| commence  | ? extends org.bukkit.event.Event | null                 | apply    | 指定该事件段落的起始事件                                     |
| interrupt | ? extends org.bukkit.event.Event | null                 | apply    | 指定该事件段落的终止事件                                     |
| entity    | boolean                          | false                | apply    | 是否对 EntityEvent 进行单个实体所触发的事件进行区分          |
| player    | boolean                          | false                | apply    | 是否对 PlayerEvent 进行单个玩家所触发的事件进行区分          |
| ignore    | boolean                          | false                | apply    | 是否忽略其他事件取消该事件并继续处理该事件                   |
| priority  | org.bukkit.event.EventPriority   | EventPriority.NORMAL | apply    | 事件的监听等级 LOWEST 为最先监听 HIGHEST 为最后监听          |
| listener  | Consumer\<T\>                    | null                 | apply    | 对所监听的事件进行处理                                       |
| filter    | Predicat\<T\>                    | null                 | append   | 可以多次设置过滤 与 Stream 类似 若 filter 语句返回 true 则继续执行下一个 filter 直至执行 listener 处理事件 |

### 计数器事件注册器

*⚠️ count 与 chain 事件注册器暂时无法正常使用 正在排查错误*

### 事件链注册器

*⚠️ count 与 chain 事件注册器暂时无法正常使用 正在排查错误*

## 扩展 Extra

| 入口类方法 | 返回值类型 | 功能                                                         |
| ---------- | ---------- | ------------------------------------------------------------ |
| tick()     | void       | 提供事件名为 TickEvent 的事件 该事件每游戏 tick （约为 1 / 20 自然秒）触发一次 |
| second()   | void       | 提供事件名为 SecondEvent 的事件 该事件每自然秒触发一次       |



## 服务端、依赖与 NMS Server

| 入口类方法  | 返回值类型                                                   | 功能                       |
| ----------- | ------------------------------------------------------------ | -------------------------- |
| dependent() | [DependManagerFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/server/DependManager.java) | 用于获取插件的依赖插件实例 |
| depend()    | [DependOptional](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/server/DependOptional.java) | 检测依赖是否存在           |

## 任务 Task

| 入口类方法 | 返回值类型                                                   | 功能           |
| ---------- | ------------------------------------------------------------ | -------------- |
| simple()   | [SimpleTaskRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/task/SimpleTaskRegister.java) | 简单任务注册器 |