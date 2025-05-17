package com.helpdeskhub.auth.service;

import com.helpdeskhub.auth.dto.ValidationRequestDTO;
import com.helpdeskhub.auth.dto.ValidationResponseDTO;
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

    public AuthResponseDTO authenticate(ValidationRequestDTO request) {
        try {
            ValidationResponseDTO userInfo = webClient.post()
                    .uri("/validate")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ValidationResponseDTO.class)
                    .block();

            if (userInfo != null) {
                String token = jwtService.generateToken(request.getEmail());
                Integer id = userInfo.getId();
                String role = userInfo.getRole();
                return new AuthResponseDTO(token, id, role);
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } catch (WebClientResponseException e) {
            throw new RuntimeException("User service error: " + e.getMessage());
        }
    }
}