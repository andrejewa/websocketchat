package andrejewa.websocketchat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static andrejewa.websocketchat.configs.AppConfig.CHAT_DESTINATION;
import static andrejewa.websocketchat.configs.AppConfig.USER_ATTRIBUTE;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final ChatMessageService chatMessageService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event)
    {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if (headerAccessor.getSessionAttributes() == null) return;

        String username = (String) headerAccessor.getSessionAttributes().get(USER_ATTRIBUTE);
        if (username == null) return;

        // save message if user left
        chatMessageService.sendLeaveNotification(CHAT_DESTINATION, username);

        // remove user from online users' list
        chatMessageService.removeAndSendOnlineUsers(CHAT_DESTINATION, username);
    }
}

