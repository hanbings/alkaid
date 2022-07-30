<h1 align="center">Alkaid Message 模块</h1>
<h6 align="center">We also provide documentation in English. <a href="../#/">Click here to go</a></h6>

## 使用模块

引入 Message 模块（注意替换版本号）

**Maven**

```xml
<dependency>
  <groupId>com.alkaidmc.alkaid</groupId>
  <artifactId>alkaid-message</artifactId>
  <version>{{alkaid.version}}</version>
</dependency>
```

**Gradle**

```groovy
implementation "com.alkaidmc.alkaid:alkaid-message:{{alkaid.version}}"
```

**Gradle Kotlin**

```kotlin
implementation("com.alkaidmc.alkaid:alkaid-message:{{alkaid.version}}")
```

**创建模块引导类**

```java
new AlkaidMessage();
```

| 入口类方法 | 返回值类型            | 功能                         |
| -------- | ------------------- | ---------------------------- |
| `text()` | [`JsonTextBuilder`] | [聊天文本构建器](#聊天文本构建器) |


## 聊天文本构建器

此处的聊天文本指**原始JSON文本（Raw JSON Text）**，其可用于在 Minecraft 中的许多地方显示富文本，如聊天栏、物品或实体名称等。

### 渐进式教程

首先，创建一个聊天文本构建器实例，这里我们将它存为一个变量以便进行教程。

```java
var builder = new AlkaidMessage().text();
```

添加一个文本，可以使用 `text` 方法，其有三个定义：

* `text(String text, Format... formats)`
* `text(String text, ChatColor color, Format... formats)`
* `text(String text, String color, Format... formats)`

其中 [`Format`][] 是一个枚举类，用以表示 **粗体**、*斜体*、<span style="text-decoration: underline;">下划线</span>、<span style="text-decoration: line-through">删除线</span> 等样式效果。

例如，一个粗体的 `Hello`（**Hello**），可以像这样创建：

```java
builder.text("Hello", Format.BOLD);
```

也可以使用一些方便的方法来替代。

```java
builder.bold("Hello");
```

这个方法的实现如下。

```java
public JsonTextBuilder bold(String text) {
    return text(text, Format.BOLD);
}
```

使用 `components()` 方法可以获取生成的聊天文本，并发送给玩家。

```java
var message = builder.bold("Hello").components();
player.spigot().sendMessage(message);
```

同时，Alkaid 还支持一些其他的文本类型，其中包括 `translate`、`keybind`，以及不常用的 `score`、`selector`（可以用 `text` 替代）。关于他们的效果，参见 [原始JSON文本格式（Minecraft Wiki）](https://minecraft.fandom.com/zh/wiki/%E5%8E%9F%E5%A7%8BJSON%E6%96%87%E6%9C%AC%E6%A0%BC%E5%BC%8F)。

我们将这些方法称为**添加方法**，另外还有一类方法是**全局效果方法**。

### 全局效果方法

全局效果方法用于设置全局效果，即如果没有特殊指定，生成的聊天文本都会有指定效果。

其中包括**全局样式方法**，如 `yellow()`、`bold()` 等，用于设置颜色与样式。

<style>
  .carrot > code {
    transition: color .5s;
  }

  .carrot > code:hover {
    color: #E682A0;
  }
</style>

如下代码中，指定了全局颜色为 <span class="carrot">`#E682A0`</span>，生成效果为：<span style="color: #E682A0;">Carrot</span> <span style="color: #FF5555;">Rabbit</span> <span style="color: #E682A0;">King</span>

```java
builder.color("#E682A0")
        .text("Carrot ")
        .red("Rabbit ")
        .text("King");
```

#### 悬浮效果

用于添加悬浮效果的方法 `hover` 有如下四个定义：

* `hover(HoverEvent event)`
* `hover(Content content)`
* `hover(ContentBuilder<?> builder)`
* `hover(Function<ContentBuilder.Provider, ContentBuilder<?>> function)`

[`Content`] 是鼠标悬浮在文字上时（在聊天栏或书中）显示的内容，可以是**聊天文本**、**物品信息**或**实体信息**，我们将这三者称为**内容类型**。

Alkaid 提供了一个 [`ContentBuilder`] 接口用于构建 `Content`，每个内容类型都有一个对应的 `ContentBuilder` 实现。同时，`JsonTextBuilder` 自身就是聊天文本类型的实现。

`hover` 方法的最后一个定义以一个函数式接口为参数，提供一个 [`ContentBuilder.Provider`]，并接受一个 `ContentBuilder`。

`ContentBuilder.Provider` 有三个方法，分别用于引导生成对应的 `ContentBuilder`。示例如下。

```java
builder.text("Hello")
        .hover(hover -> hover.text().text("World"));
```

其他的全局效果方法还包括：`insertion`、`font`、`click`

### 文本嵌套

制作复杂文本时需要进行文本嵌套，可以使用 `append` 方法实现这个操作。示例如下。

```java
builder.append(it -> it.font("custom:carrot")
                .text("Hello"))
        .red(", ")
        .text("World", "#E682A0", Format.BOLD);
```

这里的 `font` 方法虽然定义了全局样式（字体），但只作用于 `Hello` 那一层的文本，外层文本不受限制。

### 端点操作

| 方法名              | 返回值类型          | 功能                  |
| ------------------ | ----------------- | -------------------- |
| `components()`     | `BaseComponent[]` | 获取聊天文本           |
| `pureComponents()` | `BaseComponent[]` | 获取聊天文本（无全局样式）|
| `raw()`            | `String`          | 获取聊天文本的源格式   |
| `plain()`          | `String`          | 获取聊天文本的显示效果   |

[`JsonTextBuilder`]: https://github.com/AlkaidMC/alkaid/blob/main/alkaid-message/src/main/java/com/alkaidmc/alkaid/message/text/JsonTextBuilder.java
[`Format`]: https://github.com/AlkaidMC/alkaid/blob/main/alkaid-message/src/main/java/com/alkaidmc/alkaid/message/text/Format.
[`Content`]: https://ci.md-5.net/job/BungeeCord/ws/chat/target/apidocs/net/md_5/bungee/api/chat/hover/content/Content.html
[`ContentBuilder`]: https://github.com/AlkaidMC/alkaid/blob/main/alkaid-message/src/main/java/com/alkaidmc/alkaid/message/text/hover/ContentBuilder.java
[`ContentBuilder.Provider`]: https://github.com/AlkaidMC/alkaid/blob/main/alkaid-message/src/main/java/com/alkaidmc/alkaid/message/text/hover/ContentBuilder.java#L32
