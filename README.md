**ABOUT**

The app is a WebChat with WebSockets. The User prints his/her name, and then enters the chat. The User can see the latest 10-messages from the chat, and the list of online Users. The entire chat history as well as the connected Users’ details are saved in DB.

**QUICK START**

Run the following commands in the project directory:

```sh
$ cp .env.default .env
$ mvn spring-boot:run
```

**BUILD**

Build the project:
```sh
$ mvn package
```

**RUN**

The artifact `websocketchat-<project.version>.jar` is located in `target` directory. Also `.env` file is required, set your environment variables there, for example:
```
DB_USERNAME=postgres_user
DB_PASSWORD=postgres_pass
DB_HOST=postgres_host
DB_NAME=postgres_dbname
DB_PORT=5432
APP_PORT=8080
```
The directory structure should look like that:

```sh
$ ls -a
.env
websocketchat-<project.version>.jar
```

Run the project:
```sh
$ java -jar websocketchat-<project.version>.jar
```

**WEB**

`http://localhost:{APP_PORT}/`

**TOOLS**

Java 17, SpringBoot 3, Spring Data JPA, Maven, PostgreSQL, WebSocket

**LICENSE**

Licensed under the MIT license.