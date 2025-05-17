package com.helpdeskhub.auth.controller;

import com.helpdeskhub.auth.dto.AuthRequestDTO;
import com.helpdeskhub.auth.dto.AuthResponseDTO;
import com.helpdeskhub.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponseDTO authenticate(@RequestBody AuthRequestDTO request) {
        return authService.authenticate(request);
    }
}