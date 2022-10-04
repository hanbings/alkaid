![Alkaid](https://picture.hanbings.com/2022/07/29/84aa54b568322.png)

<h1 align="center">🌟 Alkaid</h1>
<h5 align="center">A Lightweight Kit Assisting In Developing.</h5>
<h5 align="center">🚧 Release 即将发布 在此之前请勿用于生产环境 感谢支持！</h5>

## 🍀 这是什么？

一只工具库，封装 Bukkit BungeeCord 以及其他计划中的 Minecraft 服务端 API

**来点简单的事件监听**

使用 Alkaid 提供的链式 API，可以省去原先使用 Bukkit API 定义监听器所需的继承再重写的繁杂步骤。

```java
new AlkaidEvent(plugin).simple()
                // 监听的事件
                .event(PlayerJoinEvent.class)
                // 事件处理器
                .listener(event -> {
                    event.getPlayer().sendMessage("欢迎");
                })
                // 事件优先级
                .priority(EventPriority.HIGHEST)
                // 忽略取消标志位
                .ignore(false)
                // 将事件注册到 Bukkit 事件系统
                .register();
```

**事件段落**

事件段落由头部事件 - 主逻辑事件处理器 - 尾部事件组成。

监听到头部事件后才会执行事件处理器，最后由尾部事件挂起监听器，直到监听到下一个头部事件重新开始段落。

```java
new AlkaidEvent(plugin).section()
                .event(PlayerBedEnterEvent.class)
                .listener(event -> {
                    event.getPlayer().sendMessage("晚安");
                })
                // 分别处理每一个实体 也就是说每一个实体对应一个段落
                // 开启后段落只接受继承 PlayerEvent 或 EntityEvent 的事件
                .entity(true)
                // 过滤不符合条件的事件
                .filter(event -> event.getPlayer().isSleeping())
                // 监听到此事件时开始监听
                .commence(PlayerBedEnterEvent.class)
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
                .permission("alkaid.permission")
                .usage("/alkaid")
                .aliases(List.of("alias"))
                .executor((sender, command, label, args) -> {
                    sender.sendMessage("你好！");
                    return true;
                })
                .tab((sender, command, alias, args) -> List.of("你好"))
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
                .inventory();
```

**物品堆构造器**

```java
new AlkaidInventory(plugin).item()
                // 从现有的 ItemStack ItemMeta 或 Material 创建一个新的 ItemStackBuilder
                .of(Material.DIAMOND_SWORD)
                .of(new ItemStack(Material.DIAMOND_SWORD))
                // 可堆叠物品堆叠数量
                .amount(1)
                // 附魔效果
                .enchantment(Enchantment.DAMAGE_ALL, 1)
                // 标记位
                .flag(ItemFlag.HIDE_ENCHANTS)
                // 名称
                .display("小蛋糕")
                // 添加 lore 或 多行 lore
                .lore("这是一个小蛋糕")
                .lore("吃掉小蛋糕", "吃掉吃掉")
                // 本地化键
                .localized("alkaid.inventory.cake")
                // custom model data
                .model(1)
                // 设置物品的 unbreakable 标签是否为 true.
                .unbreakable(false)
                .item();
```

**Json 文本生成**

```java
new AlkaidMessage(plugin).text()
                .append(it -> it.text("Hello")
                        .yellow()
                        .bold()
                        .underlined()
                        .hover(hover -> hover.text().text("一眼翻译，鉴定为：再见")))
                .red(", ")
                .text("World", "#E682A0", Format.BOLD)
                .components();
```

<details>

<summary>生成效果</summary>

![效果图](https://picture.hanbings.com/2022/05/08/56562eb28cce1.png)

```json
{
	"extra": [{
		"bold": true,
		"underlined": true,
		"color": "yellow",
		"hoverEvent": {
			"action": "show_text",
			"contents": [{
				"extra": [{
					"text": "一眼翻译，鉴定为：再见"
				}],
				"text": ""
			}]
		},
		"extra": [{
			"text": "Hello"
		}],
		"text": ""
	}, {
		"color": "red",
		"text": ", "
	}, {
		"bold": true,
		"color": "#E682A0",
		"text": "World"
	}],
	"text": ""
}
```

</details>

**辅助反射**

```java
new AlkaidCommon().reflection()
                // 设置类 / 字符串类路径
                .clazz(AlkaidCommon.class)
                // 指定类加载器
                .loader(this.getClass().getClassLoader())
                // 是否初始化类
                .initialize(true)
                // 设置方法名
                .method("test")
                // 设置执行方法实例
                .instance(null)
                // 设置方法参数
                .args(null)
                // 自定义异常
                .exception(Throwable::printStackTrace)
                // 设置执行结果处理器
                .result(System.out::println)
                // 执行
                .call();
```

**文件监控**

```java
new AlkaidCommon().filewatchdog()
                .path(Paths.get("alkaid.txt"))
                // 监听变化的频率
                .delay(1000)
                // 状态变更时触发
                .create(f -> System.out.println("创建"))
                .modify(f -> System.out.println("修改"))
                .delete(f -> System.out.println("删除"))
                // 自定义异常处理
                .exception(e -> System.out.println("异常"))
                .watch();
```

## ✨ 模块

| 模块              | 描述                                | Bukkit 支持 | Bungee Cord 支持 | 不依赖于 Bukkit / Bungee Cord |
| ----------------- | ----------------------------------- | ----------- | ---------------- | ----------------------------- |
| alkaid-bukkit     | Bukkit API 流式封装                 | ✔️           | ❌                | ❌                             |
| alkaid-bungeecord | Bungee Cord 流式封装                | ❌           | ✔️                | ❌                             |
| alkaid-common     | 服务端无关工具类 如反射 sha256      | ✔️           | ✔️                | ✔️                             |
| alkaid-inventory  | 物品和物品容器封装                  | ✔️           | ❌                | ❌                             |
| alkaid-log        | 控制台 Logger 封装 包括色彩         | ✔️           | ✔️                | ✔️                             |
| alkaid-message    | 表达信息类封装                      | ✔️           | ⭕️                | ❌                             |
| alkaid-mongodb    | 对于 MongoDB 数据库的封装           | ✔️           | ✔️                | ✔️                             |
| alkaid-redis      | 对于 Redis 中间件的封装             | ✔️           | ✔️                | ✔️                             |
| alkaid-world      | 维度、群系与方块                    | ✔️           | ❌                | ❌                             |

<h6 align="center">✔️ 表示支持 ⭕️ 表示部分支持 ❌ 表示不支持 </h6>

## 🐌 计划列表

通过计划列表可以知道我们正在进行什么工作以及计划进行什么工作。

同样的，欢迎 PR 为龙龙添加功能，但最好事先与我们讨论一下，避免重复实现。

[Alkaid Development](https://github.com/AlkaidMC/alkaid/projects/1)

如果希望我们实现某些功能可以通过 Issues 告诉我们，关于 Issues / PR 有一些要求，请往下阅读。

## ⚡️ 快速开始

目前 Alkaid Lib 发布在 https://repository.alkaidmc.com

需要通过 Maven 或 Gradle 添加自定义仓库再添加对应模块依赖：

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
maven { url "https://repository.alkaidmc.com/releases" }
maven { url "https://repository.alkaidmc.com/snapshots" }
```

**Gradle Kotlin**

```kotlin
maven { url = uri("https://repository.alkaidmc.com/releases") }
maven { url = uri("https://repository.alkaidmc.com/snapshots") }
```

## 📝 文档

我们正在编写文档...

[查看文档](https://alkaid.alkaidmc.com/docs/#/zh-cn/readme)

## 💬 贡献

**什么是贡献？**

贡献是协助或参与我们开发的过程，包括但不限于向我们报告漏洞、请求合理的新功能和提交代码。

对于 Issues / PR 以及其他可能的一些贡献我们有一些特殊的要求，还请仔细看一看，感谢支持 w

[查看贡献指南](https://alkaid.alkaidmc.com/docs/#/zh-cn/contributing)

## ⚖ 开源许可

本项目使用 [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.html) 许可协议进行开源。

本项目是**非盈利性**项目。

依据协议，本项目**允许**遵守协议的前提下基于本项目开发的项目**进行商用**，但需要注意的是，本项目中的图片（包括 Readme.md 文件中所展示的吉祥物狼龙摇光和可能出现的其他图片）**不属于开源的范围**
它们属于开发者 [寒冰 hanbings](https://github.com/hanbings) 个人所有，~~是寒冰的崽子~~，请在复制、修改本项目时**移除它们**。

**版权警告：吉祥物狼龙摇光图片中所使用 Alkaid 字样字体为商业需授权字体 Snap ITC**

## 🍀 关于开源

开源是一种精神。

开源运动所坚持的原则：

1. 坚持开放与共享，鼓励最大化的参与与协作。
2. 尊重作者权益，保证软件程序完整的同时，鼓励修改的自由以及衍生创新。
3. 保持独立性和中立性。

与来自五湖四海的开发者共同**讨论**技术问题，**解决**技术难题，**促进**应用的发展是开源的本质目的。

**众人拾柴火焰高，开源需要依靠大家的努力，请自觉遵守开源协议，弘扬开源精神，共建开源社区！**

