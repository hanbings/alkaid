<h1 align="center">关于 Alkaid 的设计与实现</h1>
<h6 align="center">We also provide documentation in English. <a href="../#/">Click here to go</a></h6>

## 概要

为了提高 Bukkit 插件的开发效率，摇光对 Bukkit API 基于链式调用与流（Stream）思想进行了再封装。

## 概念

为了理解摇光的设计思路，这是一些必要概念。

### JSON

### 数据实体

数据实体，指用于描述某一样事物但仅包含这样事物的数据含义的特殊数据类。

**举一个具体化的例子**

这是一个 JSON 数据：

```json
{
  "database": "database",
  "host": "localhost",
  "port": "27017",
  "username": "root",
  "password": "root"
}
```

它用来描述一个数据库的信息，现在来创建一个 Java 对象来表达它：

```java
public class Database {
    String database;    // 数据库名
    String host;        // 数据库 IP
    int port;           // 端口
    String username;    // 用户名
    String password;    // 密码
}
```

**这有什么关联呢？**

首先，JSON 与 Java 对象的结构是一致的，这就意味着，它们都可以表达同一个数据体。

然后，注意到 Java 对象中没有对这些数据进行任何处理，没有连接数据库，也没有将 `root` 用户名和密码上传到某一个服务器里，那么，这一个对象就符合了对数据实体下的定义：仅包含这样事物的数据含义，这就是一个数据实体。

### 链与调用链

### 注解

### Lombok

### @FunctionInterface 与函数