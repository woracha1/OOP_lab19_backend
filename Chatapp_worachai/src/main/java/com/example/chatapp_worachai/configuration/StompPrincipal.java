package com.example.chatapp_worachai.configuration;

import java.security.Principal;

public record StompPrincipal(String username) implements Principal {

    @Override
    public String getName() {
        return username;
    }
}