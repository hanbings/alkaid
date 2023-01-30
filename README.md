![Alkaid](https://picture.hanbings.com/2022/07/29/84aa54b568322.png)

<h1 align="center">🌟 Alkaid</h1>
<h5 align="center">A Lightweight Kit Assisting In Developing.</h5>
<h5 align="center">🚧 It will be released soon, please do not use it in production environment before that, thanks for your support!</h5>

[[English]](./READMD.md) [[简体中文]](./README.zh-CN.md)

## 🍀 What is this?

A utility library wrapping Bukkit, BungeeCord, and other planned Minecraft server-side APIs.

**Target:**

1. A set of Bukkit API that uses Java secondary packaging.
2. More friendly API forms: chain calls and streams.
3. Detailed documentation and Javadocs help users understand and use the code.
4. As low coupling as possible.
5. The smallest possible final package volume.

**Table of Contents:**

- What is this?
- Quick start
- Instructions for use
- Example
- Module
- Plan
- Documentation
- Contribution (PR, Issue and other forms of contribution)
- Code of Conduct
- Open Source License
- About Open Source

## ⚡️ Quick start

Currently Alkaid Lib is released at https://repository.alkaidmc.com

This requires adding a custom repository through Maven or Gradle and then adding the corresponding module dependencies:

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

## 🚀 Instructions for use

**Compile**: Build using Gradle.

```bash
# clone from our repository.
$ git clone https://github.com/hanbings/alkaid
# source dir.
$ cd alkaid
# complie and package with gradle build.
$ gradle build
```

## ✨ Example

**Let's do some simple event monitoring**

Using the chain API provided by Alkaid can save the troublesome steps of inheritance and rewriting that were originally required to define listeners using the Bukkit API.

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

**Event Section**

The event section consists of head event - main logic event handler - tail event.

The event handler will not be executed until the head event is monitored, and finally the tail event will suspend the listener until the next head event is detected and the Section will be restarted.

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

**Register Command**

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

**Register Task**

```java
new AlkaidTask(plugin).simple()
                .run(() -> System.out.println("快和我一起歌唱 好孩子才不怕悲伤"))
                .delay(20)
                .period(20)
                .async(true)
                .register();
```

**Create a Book**

```java
new AlkaidInventory(plugin).book()
                .title("这是一本书")
                .author("这是一本书的作者")
                .write("这是往书里写了一句话")
                .write(2, "这是往第三页写了一句话")
                // 生成书的 ItemStack
                .written();
```

**Custom Inventory**

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

**ItemStack Builder**

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

**Json Message**

```java
new AlkaidMessage(plugin).text()
                .append(it -> it.text("Hello")
                        .yellow()
                        .bold()
                        .underlined()
                        .hover(hover -> hover.text("一眼翻译，鉴定为：再见")))
                        // full ver: ... hover.text().text("一眼翻译，鉴定为：再见") ...
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

**Reflection**

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

**File Watchdog**

```java
new AlkaidCommon().filewatchdog()
                .path(Paths.get("alkaid.txt"))
                // 监听变化的频率
                .delay(1000)
                // 状态变更时触发
                .create(f -> System.out.println("create"))
                .modify(f -> System.out.println("modify"))
                .delete(f -> System.out.println("delete"))
                // 自定义异常处理
                .exception(e -> System.out.println("exception"))
                .watch();
```

## 📦 Module

| 模块              | 描述                           | Bukkit 支持 | Bungee Cord 支持 | 不依赖于 Bukkit / Bungee Cord |
| ----------------- | ------------------------------ | ----------- | ---------------- | ----------------------------- |
| alkaid-bukkit     | Bukkit API 流式封装            | ✔️           | ❌                | ❌                             |
| alkaid-bungeecord | Bungee Cord 流式封装           | ❌           | ✔️                | ❌                             |
| alkaid-common     | 服务端无关工具类 如反射 sha256 | ✔️           | ✔️                | ✔️                             |
| alkaid-inventory  | 物品和物品容器封装             | ✔️           | ❌                | ❌                             |
| alkaid-log        | 控制台 Logger 封装 包括色彩    | ✔️           | ✔️                | ✔️                             |
| alkaid-message    | 表达信息类封装                 | ✔️           | ⭕️                | ❌                             |
| alkaid-metadata   | 处理 NBT 和 Region 原始数据    | ✔️           | ❌                | ❌                             |
| alkaid-mongodb    | 对于 MongoDB 数据库的封装      | ✔️           | ✔️                | ✔️                             |
| alkaid-organism   | 提供生物 AI 框架               | ✔️           | ❌                | ❌                             |
| alkaid-redis      | 对于 Redis 中间件的封装        | ✔️           | ✔️                | ✔️                             |
| alkaid-world      | 维度、群系与方块               | ✔️           | ❌                | ❌                             |

<h6 align="center">✔️ 表示支持 ⭕️ 表示部分支持 ❌ 表示不支持 </h6>

## 🐌 Plan

Through the plan list, you can know what we are working on and what work we plan to do.

PRs are welcome, but it's best to discuss them with us beforehand to avoid re-implementation.

[Alkaid Development](https://github.com/AlkaidMC/alkaid/projects/1)

If you want us to implement some functions, you can tell us through Issue. There are some requirements for Issue / PR, please read on.

## 📝 Documentation

Detailed documentation is presented in Github Pages, and their source files are in the `docs` directory of this repository.

[View document](https://alkaid.alkaidmc.com/docs/#/)

## 💬 Contribution

**What is a contribution?**

Contributing is the process of assisting or participating in our development, including but not limited to reporting bugs to us, requesting reasonable new features, and submitting code.

We have some special requirements for Issue / PR and other possible contributions, please take a closer look, thanks for your support

[View Contribution Guide](https://alkaid.alkaidmc.com/docs/#/zh-cn/contributing)

## 🍺 Code of Conduct

We are writing a code of conduct...

## ⚖ Open Source License

This project is open sourced using the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.html) license agreement.

This project is a **non-profit** project.

According to the agreement, this project **allows** commercial use** based on the project developed by this project under the premise of abiding by the agreement, but it should be noted that the pictures in this project (including the Alkaid displayed in the Readme.md file and other images that may appear) **Not part of the scope of open source**
They belong to the developer [Hanbings](https://github.com/hanbings), please **remove them** when copying or modifying this project.

**Copyright Warning: The Alkaid font used in the mascot flutter image is a commercially licensed font Snap ITC**

## 🍀 About Open Source

Open source is a spirit.

Principles of the open source movement:

1. Adhere to openness and sharing, and encourage maximum participation and collaboration.
2. Respect the rights and interests of authors, while ensuring the integrity of the software program, encourage the freedom of modification and derivative innovation.
3. Maintain independence and neutrality.

Discussing technical issues with developers from all over the world, solving technical problems and promoting the development of applications are the essential purposes of open source.

**Everyone gathers firewood and the fire is high. Open source needs to rely on everyone's efforts. Please consciously abide by the open source agreement, promote the spirit of open source, and build an open source community together!**

