package andrejewa.websocketchat.enums;

import java.io.Serializable;

public enum MessageType implements Serializable {
    CHAT,
    JOIN,
    LEAVE,
    USERS
}
