package com.example.chatapp_worachai.controller;

import com.example.chatapp_worachai.DTO.CreateMessage;
import com.example.chatapp_worachai.DTO.EditMessage;
import com.example.chatapp_worachai.model.MessageModel;
import com.example.chatapp_worachai.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageRepository messageRepository;

    @GetMapping
    public List<MessageModel>  getMessages() {
        return messageRepository.getMessages();
    }

    @PostMapping("/send")
    public boolean sendMessage(@RequestBody CreateMessage message) {
        return messageRepository.sendMessage(message.getUserID(), message.getMessage());
    }

    @DeleteMapping("/{userID}/{messageID}")
    public boolean deleteMessage(@PathVariable UUID userID, @PathVariable UUID messageID) {
        return messageRepository.deleteMessage(userID, messageID);
    }

    @GetMapping("/search")
    public List<MessageModel> searchMessages(@RequestParam("filter") String keyword) {
        return messageRepository.searchMessages(keyword);
    }

    @PutMapping("/edit")
    public boolean editMessage(@RequestBody EditMessage request) {
        return messageRepository.editMessage(
                request.getUserID(),
                request.getMessageID(),
                request.getNewMessage()
        );
    }


}
