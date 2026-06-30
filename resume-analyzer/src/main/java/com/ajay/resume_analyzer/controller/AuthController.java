package com.ajay.resume_analyzer.controller;

import com.ajay.resume_analyzer.dto.AuthResponse;
import com.ajay.resume_analyzer.dto.LoginRequest;
import com.ajay.resume_analyzer.dto.RegisterRequest;
import com.ajay.resume_analyzer.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        System.out.println("LOGIN ENDPOINT HIT");

        return ResponseEntity.ok(authService.login(request));
    }
}