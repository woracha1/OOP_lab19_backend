package com.example.chatapp_worachai.controller;

import com.example.chatapp_worachai.DTO.CreateUser;
import com.example.chatapp_worachai.model.UserModel;
import com.example.chatapp_worachai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // add new
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @PostMapping("/add")
    public UUID addUser(@RequestBody CreateUser createUser) {
        return userRepository.addUser(createUser.getUsername());
    }

    @GetMapping("/user-number")
    public int getUserNumber() {
        return userRepository.getUserCount();
    }

    @GetMapping("/user-list")
    public List<UserModel> getUserList() {
        return userRepository.getUserList();
    }

}
