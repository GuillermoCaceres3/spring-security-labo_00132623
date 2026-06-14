package com.server.app.controllers;

import com.server.app.config.JsonWebToken;
import com.server.app.dto.auth.AuthResponse;
import com.server.app.dto.auth.LoginDto;
import com.server.app.dto.auth.UpdatePasswordDto;
import com.server.app.dto.user.UserCreateDto;
import com.server.app.dto.user.UserUpdateDto;
import com.server.app.entities.User;
import com.server.app.services.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JsonWebToken jsonWebToken;

    public AuthController(UserService userService, JsonWebToken jsonWebToken) {
        this.userService = userService;
        this.jsonWebToken = jsonWebToken;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse<User>> login(@Valid @RequestBody LoginDto dto) {
        User user = userService.login(dto);
        String token = jsonWebToken.createToken(user);

        return ResponseEntity.ok(new AuthResponse<>(token, user));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse<User>> signUp(@Valid @RequestBody UserCreateDto dto) {
        User user = userService.signUp(dto);
        String token = jsonWebToken.createToken(user);

        return ResponseEntity.ok(new AuthResponse<>(token, user));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> profile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/profile")
    public ResponseEntity<AuthResponse<User>> updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserUpdateDto dto
    ) {
        User updatedUser = userService.updateProfile(user.getId(), dto);
        String token = jsonWebToken.createToken(updatedUser);

        return ResponseEntity.ok(new AuthResponse<>(token, updatedUser));
    }

    @PutMapping("/update/password")
    public ResponseEntity<User> updatePassword(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdatePasswordDto dto
    ) {
        return ResponseEntity.ok(userService.updatePassword(user.getId(), dto));
    }
}