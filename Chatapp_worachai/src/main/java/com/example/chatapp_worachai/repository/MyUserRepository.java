package com.example.chatapp_worachai.repository;

import com.example.chatapp_worachai.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class MyUserRepository implements UserRepository {
    private final Map<UUID, UserModel> users = new HashMap<>();
    private int userCount;
    private boolean typing = false;

    @Override
    public UUID addUser(String username) {
        UserModel userModel = new UserModel(UUID.randomUUID(), username, false, true,0);
        users.put(userModel.getUserID(), userModel);
        incrementUserCount(); // add new
        return userModel.getUserID();
    }

    // add new
    @Override
    public void removeUserByUsername(String username) {
        users.values().removeIf(user -> user.getUsername().equals(username));
    }

    @Override
    public UserModel getUserByID(UUID id) {
        return users.get(id);
    }

//--------------------------- prelab 20 --------------------------------------//

    @Override
    public int getUserCount() {
        return userCount;
    }

    @Override
    public void incrementUserCount() {
        userCount++;
    }

    @Override
    public void decrementUserCount() {
        userCount--;
    }

//--------------------------- lab 20 --------------------------------------//
    @Override
    public List<UserModel> getUserList() {
        return new java.util.ArrayList<>(users.values());
    }

    @Override
    public List<UserModel> getTypingUsers() {
        return users.values().stream()
                .filter(UserModel::isTyping)
                .toList();
    }

    @Override
    public boolean isTyping() {
        return typing;
    }

    @Override
    public void setTyping(boolean typing) {
        this.typing = typing;
    }

    @Override
    public List<UserModel> getOnlineUsers() {
        return users.values()
                .stream()
                .filter(UserModel::isOnline)
                .toList();
    }
}


