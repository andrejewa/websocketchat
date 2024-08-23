package andrejewa.websocketchat.controllers;

import andrejewa.websocketchat.dtos.MessageDTO;
import andrejewa.websocketchat.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import static andrejewa.websocketchat.configs.AppConfig.*;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping(MESSAGE_MAPPING_SEND_MESSAGE)
    @SendTo(CHAT_DESTINATION)
    public MessageDTO processMessage(@Payload MessageDTO messageDTO)
    {
        return chatMessageService.processMessage(messageDTO);
    }

    @MessageMapping(MESSAGE_MAPPING_ADD_USER)
    @SendTo(CHAT_DESTINATION)
    public MessageDTO addUser(@Payload MessageDTO messageDTO,
                              SimpMessageHeaderAccessor headerAccessor)
    {
        return chatMessageService.addUser(messageDTO, headerAccessor);
    }
}

