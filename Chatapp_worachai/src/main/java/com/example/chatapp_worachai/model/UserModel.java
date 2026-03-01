package com.example.chatapp_worachai.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class UserModel {
    private UUID userID ;
    private String username;
}
