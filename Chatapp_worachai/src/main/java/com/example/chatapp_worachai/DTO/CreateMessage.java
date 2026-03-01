package com.example.chatapp_worachai.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter

public class CreateMessage {
    private final UUID userID;
    private String message;
}
