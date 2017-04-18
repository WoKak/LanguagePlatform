# webPlang

this is a desktop platform for learning language

## Technology stack

* Java 8
* JavaFX
* Maven 3.3.9
* PostgreSQL 9.6.2

## Features

* Simple exercise testing knowledge of randomly chosen 20 words
* Adding words to database

# There are two versions of the project:

# 1. latest version

database, dialogs, etc.

# 2. old version (unsupported)

previous version of the project


# To run this project:
 
In both cases you need to run jar file in the resources directory.
 
# 1. (database version - latest one)

At first PostgreSQL Server is needed. Then it is crucial to create database named plang.
Run jar file using command:

```
    java -jar plang(latest_ver).jar
```


# 2. (old version)

Just open the jar file.
 
-------------------------------------------------------------
 
- You also need base.txt file in both versions. (very simple one is provided in the same directory as jar file).

- In order to change base just replace base.txt file (WARNING! words should be in the same order
as before).

- As well as you need database.properties file, i.e:

```
    jdbc.drivers=org.postgresql.Driver
    
    jdbc.url=jdbc:postgresql:plang
    
    jdbc.username=postgres
    
    jdbc.password=verysecretpassword
```