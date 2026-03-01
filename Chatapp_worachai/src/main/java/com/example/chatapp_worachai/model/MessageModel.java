package com.example.chatapp_worachai.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@Getter
@Setter

public class MessageModel {
    private UUID messageID;
    private UserModel user ;
    private String message;
    private boolean deleted ;
}
