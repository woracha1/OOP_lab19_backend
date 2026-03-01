package com.example.chatapp_worachai.controller;

import com.example.chatapp_worachai.DTO.CreateUser;
import com.example.chatapp_worachai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @PostMapping("/add")
    public UUID addUser(@RequestBody CreateUser createUser) {
        return userRepository.addUser(createUser.getUsername());
    }
}
