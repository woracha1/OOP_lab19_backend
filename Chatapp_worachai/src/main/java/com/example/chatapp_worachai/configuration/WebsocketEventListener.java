package com.example.chatapp_worachai.configuration;

import com.example.chatapp_worachai.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@AllArgsConstructor
public class WebsocketEventListener {
    private final UserRepository myUserRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void onConnect(SessionConnectedEvent event) {
        myUserRepository.incrementUserCount();

        messagingTemplate.convertAndSend("/topic/user-number",
                myUserRepository.getUserCount());
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        myUserRepository.decrementUserCount();
        messagingTemplate
                .convertAndSend("/topic/user-number", myUserRepository.getUserCount());
    }

//    @EventListener
//    public void trackUser(SessionConnectedEvent event) {
//        // ต้องมั่นใจว่า Repository คืนค่าเป็น List เปล่า [] ไม่ใช่ null
//        var users = myUserRepository.getUserList();
//        if (users != null) {
//            messagingTemplate.convertAndSend("/topic/user-list", users);
//        }
//    }
}
