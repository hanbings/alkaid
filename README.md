![Alkaid](https://picture.hanbings.com/2022/07/29/84aa54b568322.png)

<h1 align="center">🌟 Alkaid</h1>
<h5 align="center">A Lightweight Kit Assisting In Developing.</h5>
<h5 align="center">🚧 It will be released soon, before which please do not use it in production environments. Thanks for your support!</h5>

[[English]](./README.md) [[简体中文]](./README.zh-CN.md)

## 🍀 What is this?

A utility library wrapping Bukkit, BungeeCord, as well as other Minecraft server-side APIs in future plans.

**Target:**

1. A set of Bukkit API secondly wrapped with Java
2. More friendly API forms: chain calls and streams
3. Detailed documentation and Javadocs helping users understand and use the code
4. As low coupling as possible
5. The smallest possible final package volume

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
        // event
        .event(PlayerJoinEvent.class)
        // event handler
        .listener(event -> {
        	event.getPlayer().sendMessage("Welcome!");
        })
        // event priority
        .priority(EventPriority.HIGHEST)
        // ignore cancel
        .ignore(false)
        // register to bukkit
        .register();
```

**Event Section**

The event section consists of head event - main logic event handler - tail event.

The event handler will not be executed until the head event is monitored, and finally the tail event will suspend the listener until the next head event is detected and the Section will be restarted.

```java
new AlkaidEvent(plugin).section()
        .event(PlayerBedEnterEvent.class)
        .listener(event -> {
            event.getPlayer().sendMessage("Good night!");
        })
        // Each entity is processed separately, that is to say, each entity corresponds to a Section. 
        // After opening, the section only accepts events that inherit PlayerEvent or EntityEvent.
        .entity(true)
        // Filter events that do not match the criteria.
        .filter(event -> event.getPlayer().isSleeping())
        // Start listening when this event is heard.
        .commence(PlayerBedEnterEvent.class)
        // Stop listening when listening to this event.
        .interrupt(PlayerBedLeaveEvent.class)
        .ignore(true)
        .priority(EventPriority.HIGHEST)
        .register();
```

**Register Command**

```java
new AlkaidCommand(plugin).simple()
         .command("alkaid")
         .description("Alkaid")
         .permission("alkaid.permission")
         .usage("/alkaid")
         .aliases(List.of("alias"))
         .executor((sender, command, label, args) -> {
         	sender.sendMessage("Hello！");
            return true;
         })
         .tab((sender, command, alias, args) -> List.of("Hello"))
         .register();
```

**Register Task**

```java
new AlkaidTask(plugin).simple()
         .run(() -> System.out.println("This is a task."))
         .delay(20)
         .period(20)
         .async(true)
         .register();
```

**Create a Book**

```java
new AlkaidInventory(plugin).book()
        .title("The title")
        .author("author")
        .write("This is a sentence written in the book.")
        .write(2, "This is a sentence written on the third page of the book.")
        // get itemStack
        .written();
```

**Custom Inventory**

```java
ItemStack book = new ItemStack(Material.BOOK);
ItemStack glass = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);

CustomInventory.ItemStackAction action = new CustomInventory.ItemStackAction()
        .click(e -> this.getLogger().info("click"))
        .left(e -> this.getLogger().info("left"))
        .right(e -> this.getLogger().info("right"))
        .drag(e -> this.getLogger().info("drag"))
        .update(e -> this.getLogger().info("update"));

@SuppressWarnings("all")
Inventory inventory = new AlkaidInventory(this).gui()
        .title("Alkaid Custom Inventory")
        .rows(3)
        .interval(2000)
        .structure(
            List.of(
                "#########",
                "####A####",
                "#########"
            ),
            Map.of(
                '#', new CustomInventory.ItemStackRegister(glass, action),
                'A', new CustomInventory.ItemStackRegister(book, action)
            )
        )
        .open(e -> ((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), "minecraft:block.note_block.pling", 1, 1))
        .close(e -> ((Player) e.getPlayer()).playSound(e.getPlayer().getLocation(), "minecraft:block.note_block.pling", 1, 1))
        .click(e -> {
            if (e.getClickedInventory() == null) return;
                if (e.getClickedInventory().getHolder() instanceof Player) return;
                e.setCancelled(true);
            })
        .drag(e -> e.setCancelled(true))
        .update((custom, inv , registries) -> {
            return registries;
        })
        .inventory();
```

**ItemStack Builder**

```java
new AlkaidInventory(plugin).item()
        // Creates a new ItemStackBuilder from an existing ItemStack ItemMeta or Material.
        .of(Material.DIAMOND_SWORD)
        .of(new ItemStack(Material.DIAMOND_SWORD))
        // set amount
        .amount(1)
        // add enchantment
        .enchantment(Enchantment.DAMAGE_ALL, 1)
        // flag
        .flag(ItemFlag.HIDE_ENCHANTS)
        // display name
        .display("This is a item")
        // Add lore or add multiple lines of lore.
        .lore("A Diamond Sword")
        .lore("Diamond", "Sword")
        // localizetion key
        .localized("alkaid.inventory.diamond.sword")
        // custom model data
        .model(1)
        // Sets the item's unbreakable tag.
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
        // set class
        .clazz(AlkaidCommon.class)
        // set classloader
        .loader(this.getClass().getClassLoader())
        // init class
        .initialize(true)
        // target method name
        .method("test")
        // set instance
        .instance(null)
        // set calling args
        .args(null)
        // catch exception
        .exception(Throwable::printStackTrace)
        // result
        .result(System.out::println)
        // call
        .call();
```

**File Watchdog**

```java
new AlkaidCommon().filewatchdog()
        .path(Paths.get("alkaid.txt"))
        // delay time / second
        .delay(1000)
        // status
        .create(f -> System.out.println("create"))
        .modify(f -> System.out.println("modify"))
        .delete(f -> System.out.println("delete"))
        // catch exception
        .exception(e -> System.out.println("exception"))
        .watch();
```

## 📦 Module

| Module            | Description                   | Running in Bukkit | Running in Bungee Cord | Runs independently as a dependency |
| ----------------- | ----------------------------- | ----------------- | ---------------------- | ---------------------------------- |
| alkaid-bukkit     | Wrapper for Bukkit API        | ✔️                 | ❌                      | ❌                                  |
| alkaid-bungeecord | Wrapper for Bungee Cord API   | ❌                 | ✔️                      | ❌                                  |
| alkaid-common     | General Java language tools   | ✔️                 | ✔️                      | ✔️                                  |
| alkaid-inventory  | Items and item containers     | ✔️                 | ❌                      | ❌                                  |
| alkaid-log        | Logger and Log colors         | ✔️                 | ✔️                      | ✔️                                  |
| alkaid-message    | Message                       | ✔️                 | ⭕️                      | ❌                                  |
| alkaid-metadata   | NBT and Region file support   | ✔️                 | ❌                      | ❌                                  |
| alkaid-mongodb    | MongoDB support               | ✔️                 | ✔️                      | ✔️                                  |
| alkaid-organism   | Biological AI framework       | ✔️                 | ❌                      | ❌                                  |
| alkaid-redis      | Redis support                 | ✔️                 | ✔️                      | ✔️                                  |
| alkaid-world      | Dimensions, biomes and blocks | ✔️                 | ❌                      | ❌                                  |

<h6 align="center">✔️ Supported ⭕️ Only Partially Supported ❌ Not Supported </h6>

## 🐌 Plan

Through the plan list, you can know what we are working on and what work we plan to do.

PRs are welcome, but it's best to discuss them with us beforehand to avoid re-implementation.

[Alkaid Development](https://github.com/AlkaidMC/alkaid/projects/1)

If you want us to implement some functions, you can tell us through Issue. There are some requirements for Issue / PR, please read on.

## 📝 Documentation

Detailed documentation is presented in Github Pages, and their source files are in the `docs` directory of this repository.

[View Documentation](https://alkaid.alkaidmc.com/docs/#/)

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

