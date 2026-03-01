package com.example.chatapp_worachai.repository;

import com.example.chatapp_worachai.model.MessageModel;
import com.example.chatapp_worachai.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MyMessageRepository implements MessageRepository {
    private final List<MessageModel> messages = new ArrayList<>();
    private final UserRepository userRepository ;

    @Override
    public List<MessageModel> getMessages() {
        return messages;
    }
    @Override
    public boolean sendMessage(UUID userID, String message) {
        UserModel userModel = userRepository.getUserByID(userID);
        if (userModel == null) {
            return false;
        }
        MessageModel messageModel = new MessageModel(UUID.randomUUID(), userModel, message, false);
        messages.add(messageModel);
        return true;
    }

    @Override
    public boolean deleteMessage(UUID userID, UUID messageID) {
        UserModel userModel = userRepository.getUserByID(userID);
        if (userModel == null) {
            return false;
        }

        MessageModel messageModel = getMessage(messageID);

        if (messageModel == null) {
            return false;
        }
        messageModel.setDeleted(true);
        return true;
    }

    @Override
    public MessageModel getMessage(UUID messageID) {
        for (MessageModel messageModel : messages) {
            if (messageModel.getMessageID().equals(messageID)) {
                return messageModel;
            }
        }
        return null;
    }

    @Override
    public List<MessageModel> searchMessages(String filter) {
        List<MessageModel> result = new ArrayList<>();

        for (MessageModel messageModel : messages) {
            if (!messageModel.isDeleted() &&
                    messageModel.getMessage().toLowerCase().contains(filter.toLowerCase())) {
                result.add(messageModel);
            }
        }
        return result;
    }

    @Override
    public boolean editMessage(UUID userID, UUID messageID, String newMessage) {

        UserModel userModel = userRepository.getUserByID(userID);
        if (userModel == null) return false ;

        MessageModel messageModel = getMessage(messageID);
        if (messageModel == null ) return false ;

        if (!messageModel.getUser().getUserID().equals(userID)) return false ;

        messageModel.setMessage(newMessage);
        return true;
    }
}
