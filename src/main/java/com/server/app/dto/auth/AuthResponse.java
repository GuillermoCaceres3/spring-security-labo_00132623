package com.server.app.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse<T> {
    private String token;
    private T data;
}