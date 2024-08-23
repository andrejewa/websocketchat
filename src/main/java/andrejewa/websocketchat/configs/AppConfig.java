package andrejewa.websocketchat.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AppConfig {

    public static final int DEFAULT_NUM_OF_LAST_MESSAGES = 10;
    public static final String USER_ATTRIBUTE = "username";
    public static final String MESSAGE_MAPPING_ADD_USER = "/chat.addUser";
    public static final String MESSAGE_MAPPING_SEND_MESSAGE = "/chat.sendMessage";
    public static final String WEB_SOCKET_ENDPOINT = "/ws";
    public static final String APP_DESTINATION_PREFIX = "/app";
    public static final String USER_DESTINATION_PREFIX = "/user";
    public static final String BROKER_DESTINATION_PREFIX = "/topic";
    public static final String CHAT_DESTINATION = BROKER_DESTINATION_PREFIX + "/public";
    public static final String REPLY_DESTINATION = BROKER_DESTINATION_PREFIX + "/reply";
}
