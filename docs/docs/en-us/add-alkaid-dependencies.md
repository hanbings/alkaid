<h1 align="center">Adding Alkaid Dependencies</h1>
<h6 align="center">We also provide documentation in English. <a href="../#/">Click here to go</a></h6>

## Adding via Maven

Add Alkaid's Maven repository between `<repositories>...</repositories>` in your pom.xml file.

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

After adding, reference the modules in your pom.xml directly within. `<dependencies>...</dependencies>`

```xml
<dependency>
  <groupId>com.alkaidmc.alkaid</groupId>
  <artifactId>alkaid-bukkit</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

The `<artifactId>alkaid-bukkit</artifactId>` refers to the module name of Alkaid called `alkaid-bukkit`. By referencing only the required modules, it can help save disk space occupied by the plugin after packaging.
[Go to GitHub to view the Alkaid module list](https://github.com/AlkaidMC/alkaid)



## Adding via Gradle

Gradle build files come in two syntax formats: Groovy and Kotlin. The difference in file names is build.gradle for Groovy and build.gradle.kts for Kotlin.

**Gradle Groovy**

Add Maven repositories:

```groovy
maven { url "https://repository.alkaidmc.com/releases" }
maven { url "https://repository.alkaidmc.com/snapshots" }
```

Add the required modules:

```groovy
implementation "com.alkaidmc.alkaid:alkaid-bukkit:1.0.0-SNAPSHOT"
```

**Gradle Kotlin**

Add Maven repositories:

```kotlin
maven { url = uri("https://repository.alkaidmc.com/releases") }
maven { url = uri("https://repository.alkaidmc.com/snapshots") }
```

Add the required modules:

```kotlin
implementation("com.alkaidmc.alkaid:alkaid-bukkit:1.0.0-SNAPSHOT")
```

[Go to GitHub to view the Alkaid module list](https://github.com/AlkaidMC/alkaid)



## Using Local Dependencies

**Not recommended for adding dependencies**

## Package Name Redirection

To avoid conflicts with other plugins using Alkaid, you need to use a package name redirection plugin.。

This is not mandatory but is recommended if you plan to publish your plugin for others to download and use.

**Maven Shade Plugin**

Add the [Shade](https://maven.apache.org/plugins/maven-shade-plugin/) plugin and configure redirection:

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

Add the [Shadow](https://imperceptiblethoughts.com/shadow/) plugin：

```groovy
plugins {
  id 'com.github.johnrengelman.shadow' version '7.1.2'
  id 'java'
}
```

Configure it:

```groovy
shadowJar {
   relocate 'com.alkaidmc.alkaid', 'your.package.name.shadow.com.alkaidmc.alkaid'
}
```