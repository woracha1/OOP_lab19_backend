package com.example.chatapp_worachai.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypingMessage {
    private String userID;
    private boolean typing;
}
