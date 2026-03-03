package com.example.chatapp_worachai.controller;

import com.example.chatapp_worachai.DTO.CreateMessage;
import com.example.chatapp_worachai.DTO.DeleteMessage;
import com.example.chatapp_worachai.DTO.EditMessage;
import com.example.chatapp_worachai.repository.MessageRepository;
import com.example.chatapp_worachai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.security.Principal;
import java.util.UUID;

@Controller
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebsocketController {
    private final MessageRepository myMessageRepository;
    private final UserRepository myUserRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    // /app/chat/message
    @MessageMapping("/chat/message")
    public void sendMessage(@Payload CreateMessage createMessage, Principal principal) {
        boolean isOperationSuccess = myMessageRepository
                .sendMessage(createMessage.getUserID(), createMessage.getMessage());
        if(isOperationSuccess){
            messagingTemplate
                    .convertAndSend("/topic/message"+myMessageRepository.getMessages());
            //broadcast to /topic/messages
        }else{
            System.out.println("Something went wrong for uuid: " + principal.getName());
            messagingTemplate
                    .convertAndSendToUser(principal.getName(),"/queue/errors","Failed send Message");
            //unicast to user that your operation failed.
        }
    }

    // /app/chat/delete-massage
    @MessageMapping("/chat/delete-message")
    public void deleteMessage(@Payload DeleteMessage deleteMessage, Principal principal) {
        UUID userID = deleteMessage.getUserID();
        UUID messageID = deleteMessage.getMessageID();
        boolean isOperationSuccess = myMessageRepository.deleteMessage(userID, messageID);
        if(isOperationSuccess){
               messagingTemplate
                       .convertAndSend("/topic/messages/delete", messageID);
               //broadcast to /topic/messages
        }else{
            System.out.println("Something went wrong for uuid: " + userID);
            messagingTemplate
                    .convertAndSendToUser(principal.getName(),"/queue/errors","Failed to delete Message");
            //unicast to user that your operation failed.
        }
    }

    // /app/chat/edit-message
    @MessageMapping("/chat/edit-message")
    public void editMessage(@Payload EditMessage editMessage, Principal principal) {
        UUID userID = editMessage.getUserID();
        UUID messageID = editMessage.getMessageID();
        String newMessage = editMessage.getNewMessage();
        boolean isOperationSuccess = myMessageRepository.editMessage(userID, messageID, newMessage);
        if(isOperationSuccess){
            messagingTemplate
                    .convertAndSend("/topic/messages/edit", editMessage);
            //broadcast to /topic/messages
        }else{
            System.out.println("Something went wrong for uuid: " + userID);
            messagingTemplate
                    .convertAndSendToUser(principal.getName(),"/queue/errors","Failed to edit Message");
            //unicast to user that your operation failed.
        }
    }
}
