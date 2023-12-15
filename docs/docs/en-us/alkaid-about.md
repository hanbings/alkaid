<h1 align="center">About the Design and Implementation of Alkaid</h1>
<h6 align="center">We also provide documentation in English. <a href="../#/">Click here to go</a></h6>

## Overview

To improve the efficiency of Bukkit plugin development, Alkaid provides a re-encapsulation of the Bukkit API based on the principles of chain calls and the Stream concept.

## Concepts

To understand the design philosophy of Alkaid, here are some essential concepts.

### JSON

### Data Entity

A data entity refers to a special data class used to describe a specific entity, containing only the data related to that entity.

**Let's provide a concrete example.**

Here is a JSON data:

```json
{
  "database": "database",
  "host": "localhost",
  "port": "27017",
  "username": "root",
  "password": "root"
}
```

It is used to describe information about a database. Now let's create a Java object to represent it:

```java
public class Database {
    String database;    // Database name
    String host;        // Database  IP
    int port;           // Port
    String username;    // Username
    String password;    // Password
}
```

**What's the connection?**

Firstly, the structure of the JSON and the Java object is the same, which means they can represent the same data entity.  

Then, notice that the Java object does not process any of this data, it doesn't connect to a database, nor does it upload the `root` username and password to any server.  

Therefore, this object conforms to the definition of a data entity: it only contains the data meaning of such an entity, making it a data entity.

### Chains and Call Chains

### Annotations

### Lombok

### @FunctionInterface and Functions
