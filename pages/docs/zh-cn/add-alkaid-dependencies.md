<h1 align="center">添加 Alkaid 依赖</h1>
<h6 align="center">We also provide documentation in English. <a href="../#/">Click here to go</a></h6>

## 使用 Maven 方式添加

在 pom.xml 的 `<repository>...</repositories>` 之间添加 Alkaid 的 Maven 仓库

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

完成添加后进行模块的引用

在 pom.xml 的 `<dependencies>...</dependencies>` 直接添加 Alkaid 模块

```xml
<dependency>
  <groupId>com.alkaidmc.alkaid</groupId>
  <artifactId>alkaid-bukkit</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

其中 `<artifactId>alkaid-bukkit</artifactId>` 的 `alkaid-bukkit` 是 Alkaid 的模块名，仅引用需要的模块能节省插件打包后占用的磁盘空间

[前往 GitHub 查看 Alkaid 模块列表](https://github.com/AlkaidMC/alkaid)



## 使用 Gradle 方式添加

gradle 的构建文件有两种语法格式，一种是 groovy，另一种是 kotlin，它们在文件名上的区别在于 build.gradle 与 build.gradle.kts

**Gradle Groovy**

添加 Maven 仓库

```groovy
maven { url "https://repository.alkaidmc.com/releases" }
maven { url "https://repository.alkaidmc.com/snapshots" }
```

添加需要的模块

```groovy
implementation "com.alkaidmc.alkaid:alkaid-bukkit:1.0.0-SNAPSHOT"
```

**Gradle Kotlin**

添加 Maven 仓库

```kotlin
maven { url = uri("https://repository.alkaidmc.com/releases") }
maven { url = uri("https://repository.alkaidmc.com/snapshots") }
```

添加需要的模块

```kotlin
implementation("com.alkaidmc.alkaid:alkaid-bukkit:1.0.0-SNAPSHOT")
```

[前往 GitHub 查看 Alkaid 模块列表](https://github.com/AlkaidMC/alkaid)



## 使用本地依赖

**不推荐使用此方法添加依赖**

## 重定向包名

避免与其他使用了 Alkaid 的插件冲突，需要使用重定向插件进行包名重定向。

这不是必须的工作，但如果你打算将你的插件发布到某一个地方供其他人下载使用则推荐进行包名重定向。

**Maven Shade Plugin**

添加 [Shade](https://maven.apache.org/plugins/maven-shade-plugin/) 插件并进行重定向配置

```xml
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.2.1</version>
        <executions>
          <execution>
            <id>shade</id>
            <phase>package</phase>
            <goals>
              <goal>shade</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <relocations>
            <relocation>
              <pattern>com.alkaidmc.alkaid</pattern>
              <shadedPattern>your.package.name.shadow.com.alkaidmc.alkaid</shadedPattern>
            </relocation>
          </relocations>
        </configuration>
      </plugin>
   </plugins>
```

**Gradle Shadow**

添加 [Shadow](https://imperceptiblethoughts.com/shadow/) 插件：

```groovy
plugins {
  id 'com.github.johnrengelman.shadow' version '7.1.2'
  id 'java'
}
```

进行配置：

```groovy
shadowJar {
   relocate 'com.alkaidmc.alkaid', 'your.package.name.shadow.com.alkaidmc.alkaid'
}
```