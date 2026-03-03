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

    @Override
    public UUID addUser(String username) {
        UserModel userModel = new UserModel(UUID.randomUUID(), username);
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

    @Override
    public int getUserCount() {
        return userCount;
    }

    @Override
    public void incrementUserCount() {
        userCount++ ;
    }

    @Override
    public void decrementUserCount() {
        userCount--;
    }

    //add new
    @Override
    public List<UserModel> getUserList() {
        return new java.util.ArrayList<>(users.values());
    }
}
