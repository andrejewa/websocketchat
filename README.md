**ABOUT**

The app is a WebChat with WebSockets. The User prints his/her name, and then enters the chat. The User can see the latest 10-messages from the chat, and the list of online Users. The entire chat history as well as the connected Users’ details are saved in DB.


**RUN**

First of all you are to set your environment variables in your .env file (or just copy from .env.default):
```
DB_USERNAME=
DB_PASSWORD=
DB_HOST=
DB_NAME=
DB_PORT=
APP_PORT=
```
Then you can run the app:
`mvn spring-boot:run`

**WEB**

`http://localhost:{APP_PORT}/`

**TOOLS**

Java 17, SpringBoot 3, Spring Data JPA, Maven, PostgreSQL, WebSocket

**LICENSE**

Licensed under the MIT license