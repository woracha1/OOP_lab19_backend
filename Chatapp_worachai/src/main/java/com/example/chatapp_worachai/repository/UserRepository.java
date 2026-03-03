package com.example.chatapp_worachai.repository;

import com.example.chatapp_worachai.model.UserModel;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
   UUID addUser(String username);
   UserModel getUserByID(UUID userID);
   //add from prelab 20
   int getUserCount();
   void incrementUserCount();
   void decrementUserCount();
   //add for prelab 20
   List<UserModel> getUserList();
   void removeUserByUsername(String username);
   List<UserModel> getTypingUsers();
   boolean isTyping() ;
   void setTyping(boolean typing);
   List<UserModel> getOnlineUsers();
}
