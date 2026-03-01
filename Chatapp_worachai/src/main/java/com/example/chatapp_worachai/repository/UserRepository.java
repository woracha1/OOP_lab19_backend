package com.example.chatapp_worachai.repository;

import com.example.chatapp_worachai.model.UserModel;

import java.util.UUID;

public interface UserRepository {
   UUID addUser(String username);
   UserModel getUserByID(UUID userID);
}
