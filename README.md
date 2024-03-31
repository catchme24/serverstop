# Servserstop

A web application for monitoring the opening servers of the game Lineage 2

## Requirements to install

* _Maven 3.9.6_
* _Java 17_
* _Docker 24.0.6_

## Installation

In root folder of project to start mock db use:

```
docker-compose up
```

This command must be run first, otherwise the project build will end with an error due to the lack of a database.

After that, also in the root folder, run:

```
mvn install
```

To start the application use:

```
java -jar serverstop-backend/target/serverstop-backend-0.0.1-SNAPSHOT.jar
```



## Usage

You're up, frontend address by default: `localhost:8080/`
<br />
Default pre configured users: none, create with registration page:)