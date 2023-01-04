<h1 align="center">Alkaid MongoDB 模块</h1>
<h6 align="center">We also provide documentation in English. <a href="../#/">Click here to go</a></h6>

## 使用模块

引入 Mongodb 模块（注意替换版本号）

**Maven**

```xml
<dependency>
  <groupId>com.alkaidmc.alkaid</groupId>
  <artifactId>alkaid-mongodb</artifactId>
  <version>{{alkaid.version}}</version>
</dependency>
```

**Gradle**

```groovy
implementation "com.alkaidmc.alkaid:alkaid-mongodb:{{alkaid.version}}"
```

**Gradle Kotlin**

```kotlin
implementation("com.alkaidmc.alkaid:alkaid-mongodb:{{alkaid.version}}")
```

**创建模块引导类**

```java
new AlkaidMongodb();
```

| 入口类方法 | 返回值类型                                                   | 功能     |
| ---------- | ------------------------------------------------------------ | -------- |
| sync()     | [SyncMongodbConnector](https://github.com/hanbings/alkaid/blob/main/alkaid-mongodb/src/main/java/com/alkaidmc/alkaid/mongodb/SyncMongodbConnector.java) | 同步实现 |
| async()    | [AsyncMongodbConnector](https://github.com/hanbings/alkaid/blob/main/alkaid-mongodb/src/main/java/com/alkaidmc/alkaid/mongodb/AsyncMongodbConnector.java) | 异步实现 |

**异步还是同步？**

MongoDB 模块同时提供了同步和异步方式使用查询的数据，取决于使用场景，但请注意，在 Bukkit 中大多数场景都应该使用同步实现，因为在 Bukkit 中绝大多数数据的处理不允许离开当前刻，也就是说异步实现是完全无效的。当然，如果数据与刻无关，可以通过以下方式进行并行运算：[优化！通过事件系统传递并行计算结果](https://alkaid.alkaidmc.com/docs/#/zh-cn/alkaid-mongodb?id=优化！通过事件系统传递并行计算结果)

## 同步

### 使用模块引导类创建同步连接器

```java
AlkaidMongodb mongodb = new AlkaidMongodb();
SyncMongodbConnection connection = mongodb.sync();
```

### 可配置变量

| 变量名   | 参数类型           | 默认值      | 传入方法 | 功能                   |
| -------- | ------------------ | ----------- | -------- | ---------------------- |
| host     | String             | "127.0.0.1" | apply    | 目标主机 IP            |
| port     | int                | 27017       | apply    | 目标主机端口           |
| database | String             | null        | apply    | 数据库名称             |
| username | String             | null        | apply    | 数据库认证用户名       |
| password | String             | null        | apply    | 数据库认证密码         |
| gson     | Gson               | new Gson(); | apply    | json 解析器            |
| options  | MongoClientOptions | null        | apply    | MongoDB 客户端配置文件 |

### 端点操作

| 方法名     | 返回值类型                                                   | 功能                                     |
| ---------- | ------------------------------------------------------------ | ---------------------------------------- |
| connect    | [SyncMongodbConnector](https://github.com/hanbings/alkaid/blob/main/alkaid-mongodb/src/main/java/com/alkaidmc/alkaid/mongodb/SyncMongodbConnector.java) | 使用配置创建 Mongo Client 返回连接器自己 |
| disconnect | void                                                         | 断开与数据库的连接                       |
| connection | [SyncMongodbConnection](https://github.com/hanbings/alkaid/blob/main/alkaid-mongodb/src/main/java/com/alkaidmc/alkaid/mongodb/SyncMongodbConnection.java) | 获取一个与数据库的连接                   |

**小心！**

`connection()` 方法必须在 `connect()` **之后**调用，`connection()` 可以多次调用获得多个连接。

**示例**

```java
SyncMongodbConnection connection = new AlkaidMongodb().sync()
                .host("localhost")
                .port(27017)
                .database("alkaid")
    			.username("alkaid")
    			.password("alkaid")
                .connect()
                .connection();
```

### 使用连接操作数据库

*限制于 Markdown 表格，无法给出参数具体含义，请参见源代码 Javadocs*

| 方法名 | 参数类型                                | 返回值类型 | 功能         |
| ------ | --------------------------------------- | ---------- | ------------ |
| create | String, Object                          | void       | 创建一个数据 |
| create | String, List\<Object\>                  | void       | 创建一堆数据 |
| update | String, Map<String, Object>, Object     | void       | 更新一个数据 |
| delete | String, Map<String, Object>             | void       | 删除一个数据 |
| read   | String, Map<String, Object>, Class\<T\> | List\<T\>  | 读取一个数据 |
| search | String, String, V, V, int, Class\<T\>   | List\<T\>  | 检索一个数据 |

**小提示**

可以使用 Map.of() 和 List.of() 简化列表和键值对创建过程

**示例**

```java
package com.alkaidmc.alkaid.mongodb;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("unused")
public class AlkaidMongodbTest {
    @Test
    public void sync() {
        SyncMongodbConnection connection = new AlkaidMongodb().sync()
                .host("localhost")
                .port(27017)
                .database("alkaid")
                .connect()
                .connection();

        // 清空数据
        connection.delete("test", new HashMap<>());
        // 写入数据
        connection.create("test",
                new Data("点一份炒饭",
                        1919810,
                        true,
                        new HashMap<>() {{
                            put("锟斤拷", "烫烫烫");
                        }},
                        new String[]{"1", "1", "4", "5", "1", "4"})
        );
        // 读取数据
        List<Data> data = connection.read("test",
                new HashMap<>() {{
                    put("message", "点一份炒饭");
                }},
                Data.class);
        // 测试数据
        assertEquals("点一份炒饭", data.get(0).message);
        assertEquals(1919810, data.get(0).number);
        assertTrue(data.get(0).flag);
        assertEquals("烫烫烫", data.get(0).map.get("锟斤拷"));
        assertEquals("1", data.get(0).array[0]);

        // 删除数据
        connection.delete("test",
                new HashMap<>() {{
                    put("message", "点一份炒饭");
                }});
        // 验证删除
        data = connection.read("test",
                new HashMap<>() {{
                    put("message", "点一份炒饭");
                }}, Data.class);
        assertEquals(0, data.size());

        // search 方法查询数据
        IntStream.range(114514, 114517).forEach(count -> {
            connection.create("test",
                    new Data("点一份炒饭",
                            count,
                            true,
                            new HashMap<>() {{
                                put("锟斤拷", "烫烫烫");
                            }}, new String[]{"1", "1", "4", "5", "1", "4"})
            );
        });

        // 查询数据
        data = connection.search("test", "number",
                114514, 114516, 10,
                Data.class);
        // 测试数据
        assertEquals(3, data.size());
    }

    @AllArgsConstructor
    static class Data {
        String message;
        int number;
        boolean flag;
        HashMap<String, String> map;
        String[] array;
    }
}
```

摘自 [Alkaid MongoDB 模块 GitHub Actions 集成测试](https://github.com/hanbings/alkaid/blob/main/alkaid-mongodb/src/test/java/com/alkaidmc/alkaid/mongodb/AlkaidMongodbTest.java) 

 `assertEquals(Object, Object)` 是比较两个值是否相等 `assertNull(Object)` 是判断值是否为 `null`

## 异步

### 使用模块引导类创建异步连接器

```java
AlkaidMongodb mongodb = new AlkaidMongodb();
AsyncMongodbConnection connection = mongodb.async();
```

### 可配置变量

| 变量名   | 参数类型           | 默认值      | 传入方法 | 功能                   |
| -------- | ------------------ | ----------- | -------- | ---------------------- |
| host     | String             | "127.0.0.1" | apply    | 目标主机 IP            |
| port     | int                | 27017       | apply    | 目标主机端口           |
| database | String             | null        | apply    | 数据库名称             |
| username | String             | null        | apply    | 数据库认证用户名       |
| password | String             | null        | apply    | 数据库认证密码         |
| gson     | Gson               | new Gson(); | apply    | json 解析器            |
| options  | MongoClientOptions | null        | apply    | MongoDB 客户端配置文件 |

### 端点操作

| 方法名     | 返回值类型                                                   | 功能                                     |
| ---------- | ------------------------------------------------------------ | ---------------------------------------- |
| connect    | [AsyncMongodbConnector](https://github.com/hanbings/alkaid/blob/main/alkaid-mongodb/src/main/java/com/alkaidmc/alkaid/mongodb/AsyncMongodbConnector.java) | 使用配置创建 Mongo Client 返回连接器自己 |
| disconnect | void                                                         | 断开与数据库的连接                       |
| connection | [AsyncMongodbConnection](https://github.com/hanbings/alkaid/blob/main/alkaid-mongodb/src/main/java/com/alkaidmc/alkaid/mongodb/AsyncMongodbConnection.java) | 获取一个与数据库的连接                   |

**小心！**

`connection()` 方法必须在 `connect()` **之后**调用，`connection()` 可以多次调用获得多个连接。

**示例**

```java
AsyncMongodbConnection connection = new AlkaidMongodb().async()
                .host("localhost")
                .port(27017)
                .database("alkaid")
    			.username("alkaid")
    			.password("alkaid")
                .connect()
                .connection();
```

### 使用连接操作数据库

*限制于 Markdown 表格，无法给出参数具体含义，请参见源代码 Javadocs*

| 方法名 | 参数类型                                                     | 返回值类型 | 功能         |
| ------ | ------------------------------------------------------------ | ---------- | ------------ |
| create | String, Object                                               | void       | 创建一个数据 |
| create | String, List\<Object\>                                       | void       | 创建一堆数据 |
| update | String, Map<String, Object>, Object                          | void       | 更新一个数据 |
| delete | String, Map<String, Object>                                  | void       | 删除一个数据 |
| read   | String, Map<String, Object>, Class\<T\>, Consumer<List\<T\>> | void       | 读取一个数据 |
| search | String, String, V, V, int, Class\<T\>, Consumer<List\<T\>>   | void       | 检索一个数据 |

**小提示**

可以使用 Map.of() 和 List.of() 简化列表和键值对创建过程

**示例**

```java
package com.alkaidmc.alkaid.mongodb;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("unused")
public class AlkaidMongodbTest {
    @Test
    public void async() {
        AsyncMongodbConnection connection = new AlkaidMongodb().async()
                .host("localhost")
                .port(27017)
                .database("alkaid")
                .thread(16)
                .connect()
                .connection();

        // 清空数据
        connection.delete("test", new HashMap<>());
        // 写入数据
        connection.create("test",
                new Data("点一份炒饭",
                        1919810,
                        true,
                        new HashMap<>() {{
                            put("锟斤拷", "烫烫烫");
                        }},
                        new String[]{"1", "1", "4", "5", "1", "4"})
        );
        // 读取数据
        connection.read("test",
                new HashMap<>() {{
                    put("message", "点一份炒饭");
                }},
                Data.class,
                (data) -> {
                    // 测试数据
                    assertEquals("点一份炒饭", data.get(0).message);
                    assertEquals(1919810, data.get(0).number);
                    assertTrue(data.get(0).flag);
                    assertEquals("烫烫烫", data.get(0).map.get("锟斤拷"));
                    assertEquals("1", data.get(0).array[0]);
                }
        );


        // 删除数据
        connection.delete("test",
                new HashMap<>() {{
                    put("message", "点一份炒饭");
                }});
        // 验证删除
        connection.read("test",
                new HashMap<>() {{
                    put("message", "点一份炒饭");
                }}, Data.class,
                (data) -> assertEquals(0, data.size()));

        // search 方法查询数据
        IntStream.range(114514, 114517).forEach(count -> {
            connection.create("test",
                    new Data("点一份炒饭",
                            count,
                            true,
                            new HashMap<>() {{
                                put("锟斤拷", "烫烫烫");
                            }}, new String[]{"1", "1", "4", "5", "1", "4"})
            );
        });

        // 查询数据
        connection.search("test", "number",
                114514, 114516, 10,
                Data.class,
                (data) -> assertEquals(3, data.size()));
    }

    @AllArgsConstructor
    static class Data {
        String message;
        int number;
        boolean flag;
        HashMap<String, String> map;
        String[] array;
    }
}

```

摘自 [Alkaid MongoDB 模块 GitHub Actions 集成测试](https://github.com/hanbings/alkaid/blob/main/alkaid-mongodb/src/test/java/com/alkaidmc/alkaid/mongodb/AlkaidMongodbTest.java) 

 `assertEquals(Object, Object)` 是比较两个值是否相等 `assertNull(Object)` 是判断值是否为 `null`

## 优化！通过事件系统传递并行计算结果