<h1 align="center">Alkaid Bukkit Module</h1>
<h6 align="center">We also provide documentation in English. <a href="../#/">Click here to go</a></h6>

## Using Modules

Import the Bukkit module (be sure to replace the version number).

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

**Create Module Bootstrap Class**

The Bukkit module includes six categories, each with its own module bootstrap class.

1. Command

   ```java
   new AlkaidCommand(Plugin plugin);
   ```

2. Configuration Files and Serialization

   ```java
   new AlkaidJsonConfiguration();
   ```

3. Event

   ```java
   new AlkaidEvent(Plugin plugin);
   ```

4. Extra

   ```java
   new AlkaidExtra(Plugin plugin);
   ```

5. Server, Dependencies, and NMS (Net Minecraft Server) Server

   ```java
   new AlkaidServer(Plugin plugin);
   ```

6. Task

   ```java
   new AlkaidTask(Plugin plugin);
   ```

## Command

| Entry Class Methods | Return Value Types                                                   | Functionality               |
| ---------- | ------------------------------------------------------------ | ------------------ |
| simple()   | [SimpleCommandRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/command/SimpleCommandRegister.java) | Simple Command Register     |
| regex()    | [RegexCommandRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/command/RegexCommandRegister.java) | Regular Expression Command Registrar |
| parse()    | [ParseCommandRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/command/ParseCommandRegister.java) | Parse Command Register   |

## Configuration Files and Serialization

### AlkaidJsonConfiguration

To extract the default JSON configuration file from the plugin file using AlkaidJsonConfiguration:

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

If your config.json file is placed at the root of your resources directory, you can load it as follows:

```json
{"test": "test"}
```

The `<T> T load(Plugin plugin, Gson gson, String resource, String path, Class<T> type)` method takes the following parameters:

`plugin` The plugin instance that holds the JSON resource.

`gson` The Gson instance used for parsing JSON. You can obtain a Gson instance with serialization adapters using the AlkaidGsonBuilder.

`resource` The location of the resource stored in the "resources" directory.

`path` The location to save the file, relative to the getDataFolder() directory of the plugin instance.

`type`  The data entity class (POJO) to which the JSON should be mapped.

**Please note that AlkaidJsonConfiguration does not automatically create missing folders, so ensure that the parent directory for the data file exists beforehand.**

### AlkaidGsonBuilder

To allow Gson to parse Bukkit objects, Alkaid provides three adapters:  [ItemStackGsonAdapter](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/config/gson/ItemStackGsonAdapter.java)、[LocationGsonAdapter](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/config/gson/LocationGsonAdapter.java) and  [PlayerGsonAdapter](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/config/gson/PlayerGsonAdapter.java) These adapters correspond to the [ItemStack](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/inventory/ItemStack.html) [Location](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Location.html) and [Player](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/Player.html), respectively.

You can obtain a Gson parser with the three adapters using the static method `gson()` located in `AlkaidGsonBuilder`. Here's how it's implemented:

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

This will give you a Gson instance that is configured with the adapters for parsing Bukkit objects, including ItemStack, Location, and Player.

Using it is as simple as a single line of code:

```java
Gson gson = AlkaidGsonBuilder.gson();
```

## Event

| Entry Class Methods | Return Value |Types                                                   |
| ----------- | ------------------------------------------------------------ | -------------------- |
| simple()    | [SimpleEventFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/AlkaidEvent.java) | This method is used for simple event registration.     |
| predicate() | [PredicateEventFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/AlkaidEvent.java) |  It is used for predicate-based event registration.       |
| section()   | [SectionEventFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/AlkaidEvent.java) | This method is used for registering events within sections or paragraphs.       |
| ~~count()~~ | ~~[CountEventFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/AlkaidEvent.java)~~ | ~~It is used for counting events.~~ |
| ~~chain()~~ | ~~[ChainEventRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/event/ChainEventRegister.java)~~ | ~~This method is used for creating event chains.~~     |

### Simple Event Registrar

#### Simple Event Registrar

| Variable Name   | Parameter Type                       | Default Value               | Method | Functionality                                                |
| -------- | ------------------------------ | -------------------- | -------- | --------------------------------------------------- |
| event    | org.bukkit.event.Event         | null                 | apply    |   Specifies the event to listen for                                     |
| ignore   | boolean                        | false                | apply    | Determines whether to ignore other event cancellations and continue processing the event          |
| priority | org.bukkit.event.EventPriority | EventPriority.NORMAL | apply    | Sets the priority level for event listening, with LOWEST being the earliest and HIGHEST being the latest |
| listener | Consumer\<T\>                  | null                 | apply    | Handles the listened event                              |

**Example**

```java
new AlkaidEvent(this).simple()
    .event(PlayerJoinEvent.class)
    .ignore(false)
    .priority(EventPriority.NORMAL)
    .listener(e -> e.getPlayer().sendMessage("你好！"))
    .register();
```

### Predicate Event Registrar

#### Predicate Event Registrar

| Variable Name   | Parameter Type                       | Default Value               | Method | Functionality                                                |
| -------- | ------------------------------ | -------------------- | -------- | ------------------------------------------------------------ |
| event    | org.bukkit.event.Event         | null                 | apply    | Specifies the event to listen for                                             |
| ignore   | boolean                        | false                | apply    | Determines whether to ignore other event cancellations and continue processing the event                   |
| priority | org.bukkit.event.EventPriority | EventPriority.NORMAL | apply    | Sets the priority level for event listening, with LOWEST being the earliest and HIGHEST being the latest          |
| listener | Consumer\<T\>                  | null                 | apply    | Handles the listened event                                       |
| filter   | Predicat\<T\>                  | null                 | append   | Allows multiple filter settings similar to Stream. If the filter statement returns true, the next filter is executed until the listener processes the event |

**Example**

```java
new AlkaidEvent(this).predicate()
    .event(AsyncPlayerChatEvent.class)
    .ignore(false)
    .priority(EventPriority.NORMAL)
    .filter(e -> e.getMessage().equals("test"))
    .listener(e -> e.getPlayer().sendMessage("你输入了 test"))
    .register();
```

You can use the `filter()` method to add a short filtering code. If the code returns `false`, the current event handler will be terminated, otherwise, the predefined processing flow in the `listener()` method will be executed. Please note that unless your code is truly very short, such as checking if a String is equal to an expected result, it is generally better to handle the processing directly in the `listener()` method.

The `filter()` method can be used multiple times in the same event handler.

### Event Paragraph Registrar

#### Configurable Variables

| Variable Name   | Parameter Type                       | Default Value               | Method | Functionality                                                |
| --------- | -------------------------------- | -------------------- | -------- | ------------------------------------------------------------ |
| event     | org.bukkit.event.Event           | null                 | apply    | Specifies the event to listen for                                             |
| commence  | ? extends org.bukkit.event.Event | null                 | apply    |   Specifies the starting event for this event paragraph                                     |
| interrupt | ? extends org.bukkit.event.Event | null                 | apply    | Specifies the terminating event for this event paragraph                                     |
| entity    | boolean                          | false                | apply    | Determines whether to differentiate events triggered by individual entities in EntityEvent          |
| player    | boolean                          | false                | apply    | Determines whether to differentiate events triggered by individual players in PlayerEvent          |
| ignore    | boolean                          | false                | apply    | Determines whether to ignore other event cancellations and continue processing the event                   |
| priority  | org.bukkit.event.EventPriority   | EventPriority.NORMAL | apply    | Sets the priority level for event listening, with LOWEST being the earliest and HIGHEST being the latest          |
| listener  | Consumer\<T\>                    | null                 | apply    |   Handles the listened event                                       |
| filter    | Predicat\<T\>                    | null                 | append   | Allows multiple filter settings similar to Stream. If the filter statement returns true, the next filter is executed until the listener processes the event |

### Counter Event Registrar

*⚠️ The count and chain event registrars are temporarily unavailable and under investigation for errors.*

### Chain Event Registrar

*⚠️ The count and chain event registrars are temporarily unavailable and under investigation for errors.*

## Extra

| Entry Class Method | Return Type | Functionality                                                         |
| ---------- | ---------- | ------------------------------------------------------------ |
| tick()     | void       | Provides an event named TickEvent, which is triggered once per game tick (approximately 1/20 of a second) |
| second()   | void       | Provides an event named SecondEvent, which is triggered once per second in real time       |

## Server, Dependencies, and NMS Server

| Entry Class Method | Return Type | Functionality                         |
| ----------- | ------------------------------------------------------------ | -------------------------- |
| dependent() | [DependManagerFactory](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/server/DependManager.java) | Used to obtain instances of dependent plugins |
| depend()    | [DependOptional](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/server/DependOptional.java) | Checks if a dependency is present           |

## Task

| Entry Class Method | Return Type | Functionality         |
| ---------- | ------------------------------------------------------------ | -------------- |
| simple()   | [SimpleTaskRegister](https://github.com/AlkaidMC/alkaid/blob/main/alkaid-bukkit/src/main/java/com/alkaidmc/alkaid/bukkit/task/SimpleTaskRegister.java) |   Simple task registrar |
