package com.example.chatapp_worachai.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class DeleteMessage {
    private UUID userID;
    private UUID messageID;
}
