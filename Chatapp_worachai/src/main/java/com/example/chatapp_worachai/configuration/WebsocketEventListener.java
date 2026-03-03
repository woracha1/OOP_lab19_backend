package com.example.chatapp_worachai.configuration;

import com.example.chatapp_worachai.model.UserModel;
import com.example.chatapp_worachai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebsocketEventListener {

    private final UserRepository myUserRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal principal = accessor.getUser();

        if (principal == null) return;

        UUID userId = UUID.fromString(principal.getName());
        UserModel model = myUserRepository.getUserByID(userId);

        if (model != null) {
            model.setSessionCount(model.getSessionCount() + 1);
            model.setOnline(true);
        }

        broadcastUsers();
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal principal = accessor.getUser();

        if (principal == null) return;

        UUID userId = UUID.fromString(principal.getName());
        UserModel model = myUserRepository.getUserByID(userId);

        if (model != null) {

            int newCount = model.getSessionCount() - 1;
            model.setSessionCount(Math.max(newCount, 0));

            if (model.getSessionCount() == 0) {
                model.setOnline(false);
            }
        }

        broadcastUsers();
    }

    private void broadcastUsers() {
        messagingTemplate.convertAndSend(
                "/topic/user-list",
                myUserRepository.getOnlineUsers()
        );

        messagingTemplate.convertAndSend(
                "/topic/user-number",
                myUserRepository.getOnlineUsers().size()
        );
    }
}