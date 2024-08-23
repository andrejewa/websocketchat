package andrejewa.websocketchat.dtos;

import andrejewa.websocketchat.enums.MessageType;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SendersDTO implements Serializable {

    @Builder.Default
    private List<String> users = new ArrayList<>();
    private MessageType type;
}
