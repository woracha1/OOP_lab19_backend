package com.example.chatapp_worachai.repository;

import com.example.chatapp_worachai.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class MyUserRepository implements UserRepository {
    private final Map<UUID, UserModel> users = new HashMap<>();

    @Override
    public UUID addUser(String username) {
        UserModel userModel = new UserModel(UUID.randomUUID(), username);
        users.put(userModel.getUserID(), userModel);
        return userModel.getUserID();
    }

    @Override
    public UserModel getUserByID(UUID id) {
        return users.get(id);
    }
}
