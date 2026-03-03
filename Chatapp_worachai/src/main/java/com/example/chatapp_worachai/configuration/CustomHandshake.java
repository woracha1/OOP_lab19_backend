package com.example.chatapp_worachai.configuration;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
public class CustomHandshake extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        String query = request.getURI().getQuery();

        if (query == null || !query.contains("=")) {
            return new StompPrincipal("anonymous-" + System.currentTimeMillis());
        }

        String[] parts = query.split("=");
        if (parts.length < 2) {
            return new StompPrincipal("anonymous-" + System.currentTimeMillis());
        }

        String uuid = parts[1];

        return new StompPrincipal(uuid);
    }
}