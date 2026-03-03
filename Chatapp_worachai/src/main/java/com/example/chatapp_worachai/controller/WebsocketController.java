package com.example.chatapp_worachai.controller;

import com.example.chatapp_worachai.DTO.CreateMessage;
import com.example.chatapp_worachai.DTO.DeleteMessage;
import com.example.chatapp_worachai.DTO.EditMessage;
import com.example.chatapp_worachai.DTO.TypingMessage;
import com.example.chatapp_worachai.model.UserModel;
import com.example.chatapp_worachai.repository.MessageRepository;
import com.example.chatapp_worachai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

    private final MessageRepository myMessageRepository;
    private final UserRepository myUserRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    // SEND MESSAGE /app/chat/message
    @MessageMapping("/chat/message")
    public void sendMessage(@Payload CreateMessage createMessage, Principal principal) {

        boolean isSuccess = myMessageRepository
                .sendMessage(createMessage.getUserID(), createMessage.getMessage());

        if (isSuccess) {
            messagingTemplate.convertAndSend(
                    "/topic/messages",
                    myMessageRepository.getMessages()
            );
        } else {
            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    "/queue/errors",
                    "Failed send Message"
            );
        }
    }

    // DELETE MESSAGE /app/chat/delete-message
    @MessageMapping("/chat/delete-message")
    public void deleteMessage(@Payload DeleteMessage deleteMessage, Principal principal) {

        boolean isSuccess = myMessageRepository
                .deleteMessage(deleteMessage.getUserID(), deleteMessage.getMessageID());

        if (isSuccess) {
            messagingTemplate.convertAndSend(
                    "/topic/messages",
                    myMessageRepository.getMessages()
            );
        } else {
            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    "/queue/errors",
                    "Failed delete Message"
            );
        }
    }

    // EDIT MESSAGE /app/chat/edit-message
    @MessageMapping("/chat/edit-message")
    public void editMessage(@Payload EditMessage editMessage, Principal principal) {

        boolean isSuccess = myMessageRepository
                .editMessage(
                        editMessage.getUserID(),
                        editMessage.getMessageID(),
                        editMessage.getNewMessage()
                );

        if (isSuccess) {
            messagingTemplate.convertAndSend(
                    "/topic/messages",
                    myMessageRepository.getMessages()
            );
        } else {
            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    "/queue/errors",
                    "Failed edit Message"
            );
        }
    }

    // TYPING /app/chat/typing
    @MessageMapping("/chat/typing")
    @SendTo("/topic/typing")
    public List<UserModel> handleTyping(TypingMessage typingMessage) {

        UUID id = UUID.fromString(typingMessage.getUserID());
        UserModel user = myUserRepository.getUserByID(id);

        if (user != null) {
            user.setTyping(typingMessage.isTyping());
        }

        return myUserRepository.getTypingUsers();
    }
}
