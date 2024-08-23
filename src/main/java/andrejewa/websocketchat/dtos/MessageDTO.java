package andrejewa.websocketchat.dtos;

import andrejewa.websocketchat.enums.MessageType;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO implements Serializable {

    private String content;
    private String user;
    private MessageType type;
    private Date date;
}
