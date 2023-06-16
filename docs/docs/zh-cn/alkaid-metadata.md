<h1 align="center">Alkaid Metadata 模块</h1>
<h6 align="center">We also provide documentation in English. <a href="../#/">Click here to go</a></h6>

## 使用模块

引入 Metadata 模块（注意替换版本号）

**Maven**

```xml
<dependency>
  <groupId>com.alkaidmc.alkaid</groupId>
  <artifactId>alkaid-metadata</artifactId>
  <version>{{alkaid.version}}</version>
</dependency>
```

**Gradle**

```groovy
implementation "com.alkaidmc.alkaid:alkaid-metadata:{{alkaid.version}}"
```

**Gradle Kotlin**

```kotlin
implementation("com.alkaidmc.alkaid:alkaid-metadata:{{alkaid.version}}")
```

**创建模块引导类**

```java
new AlkaidMetadata();
```

| 入口类方法 | 返回值类型 | 功能 |
| -------- | -------- | -------- |
| `of(Entity entity)` | [`ContainerComponentStream`] | 实体[NBT编辑器](#NBT编辑器) |
| `of(ItemStack item)` | [`ContainerComponentStream`] | 物品[NBT编辑器](#NBT编辑器) |

## NBT编辑器
**NBT编辑器**由「复合组件流」（`ContainerComponentStream`）和「列表组件流」（`ContainerListStream`）两个类组成。入口类方法返回的总是复合组件流，列表组件流可能会在编辑NBT时获取到。

### 「设置」、「添加」等方法
对于复合组件流，可以通过 `set` 系列方法修改键值对。
```java
new AlkaidMetadata().of(item)
    .set("Unbreakable", true)
    .save();
```

对于列表组件流，可以通过 `add`、`set`、`remove` 系列方法修改键值对。
```java
new AlkaidMetadata().of(item)
    .accessList("CanPlaceOn")
    .add("minecraft:amethyst_block")
    .save();
```

### 「访问」方法
访问方法包括 `access` 和 `accessList` 系列方法。

`access` 方法将改变当前操作的节点，且认为到达的节点是一个复合标签；`accessList` 认为到达的节点是一个列表标签。
```java
new AlkaidMetadata().of(entity)
    .access("ArmorItems", 3)
        .set("id", "minecraft:leather_helmet")
        .set("Count", (byte) 1)
        .accessList("tag", "Enchantments")
            .add(NBTCompound.builder()
                .entry("id", "minecraft:protection")
                .entry("lvl", (short) 10)
            )
    .save();
```

以上代码将先访问 `ArmorItems[3]`，并设置其 `id` 和 `Count` 标签；然后访问 `tag.Enchantments`，相当于访问了 `ArmorItems[3].tag.Enchantments`，并向其添加一个复合标签；最后保存。其中，`ArmorItems[3].tag` 和其下的 `Enchantments` 标签最开始是不存在的，访问时会自动创建。

### 端点操作
| 方法名 | 返回值类型 | 功能 |
| -------- | -------- | -------- |
| `data()` | 对应数据类型 | 获取当前节点NBT数据 |
| `fullData()` | [`NBTComponent`] | 获取完整NBT数据 |
| `save()` | `void` | 保存NBT到对应储存器上 |

[`ContainerComponentStream`]: https://github.com/hanbings/alkaid/blob/main/alkaid-metadata/src/main/java/com/alkaidmc/alkaid/metadata/stream/ContainerComponentStream.java
