package com.helpdeskhub.auth.service;

import com.helpdeskhub.auth.dto.AuthRequestDTO;
import com.helpdeskhub.auth.dto.AuthResponseDTO;
import com.helpdeskhub.auth.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    private final WebClient webClient = WebClient.create("http://localhost:8081/api/users");

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        try {
            // Validate credentials via user-service
            Boolean isValid = webClient.post()
                    .uri("/validate")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (Boolean.TRUE.equals(isValid)) {
                String token = jwtService.generateToken(request.getEmail());
                return new AuthResponseDTO(token);
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } catch (WebClientResponseException e) {
            throw new RuntimeException("User service error: " + e.getMessage());
        }
    }
}