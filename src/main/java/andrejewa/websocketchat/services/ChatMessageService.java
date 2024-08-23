package andrejewa.websocketchat.services;

import andrejewa.websocketchat.dtos.MessageDTO;
import andrejewa.websocketchat.dtos.SendersDTO;
import andrejewa.websocketchat.entities.ChatMessage;
import andrejewa.websocketchat.enums.MessageType;
import andrejewa.websocketchat.repositories.ChatMessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static andrejewa.websocketchat.configs.AppConfig.*;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepo chatMessageRepo;
    private final ChatSenderService chatSenderService;
    private final SimpMessagingTemplate messagingTemplate;
    private final Set<String> chatSendersOnline = new HashSet<>();

    public MessageDTO processMessage(MessageDTO messageDTO)
    {
        if (messageDTO == null) return null;

        // save message into db
        ChatMessage message = chatMessageRepo.save(
                ChatMessage
                        .builder()
                        .content(messageDTO.getContent())
                        .senderId(chatSenderService.getSenderId(messageDTO.getUser()))
                        .build()
        );

        messageDTO.setDate(message.getCreatedOn());
        return messageDTO;
    }

    private List<MessageDTO> getLastMessages(int limit)
    {
        return chatMessageRepo.findByOrderByCreatedOnDesc(Limit.of(Math.max(limit, 0)))
                .stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ChatMessage::getCreatedOn))
                .map(message -> MessageDTO
                        .builder()
                        .user(message.getSender().getName())
                        .content(message.getContent())
                        .type(MessageType.CHAT)
                        .date(message.getCreatedOn())
                        .build())
                .toList();
    }

    private void sendOnlineUsers(String destination)
    {
        var message = SendersDTO
                .builder()
                .type(MessageType.USERS)
                .users(new ArrayList<>(chatSendersOnline))
                .build();

        messagingTemplate.convertAndSend(destination, message);
    }

    public void removeAndSendOnlineUsers(String destination,
                                         String username)
    {
        if (username == null) return;
        chatSendersOnline.remove(username);
        sendOnlineUsers(destination);
    }

    private void addAndSendOnlineUsers(String destination,
                                       String username) {
        if (username == null) return;
        chatSendersOnline.add(username);
        sendOnlineUsers(destination);
    }

    public void sendLeaveNotification(String destination,
                                      String username)
    {
        var message = MessageDTO
                .builder()
                .type(MessageType.LEAVE)
                .user(username)
                .build();

        messagingTemplate.convertAndSend(destination, message);
    }

    private void sendLastMessages(String destination,
                                  String username,
                                  int num)
    {
        getLastMessages(num).forEach(message ->
                messagingTemplate.convertAndSendToUser(username, destination, message));
    }

    public MessageDTO addUser(MessageDTO messageDTO,
                              SimpMessageHeaderAccessor headerAccessor)
    {
        if (headerAccessor == null || headerAccessor.getSessionAttributes() == null || messageDTO == null) return messageDTO;

        // add username in web socket session
        String username = messageDTO.getUser();
        headerAccessor.getSessionAttributes().put(USER_ATTRIBUTE, username);

        // add user to online users' list
        addAndSendOnlineUsers(CHAT_DESTINATION, username);

        // send N last messages
        sendLastMessages(REPLY_DESTINATION, username, DEFAULT_NUM_OF_LAST_MESSAGES);

        return messageDTO;
    }
}

