package com.helpdeskhub.auth.dto;

import lombok.Data;

@Data
public class ValidationRequestDTO {
    private String email;
    private String password;
}