package com.ajay.resume_analyzer.service.auth;

import com.ajay.resume_analyzer.dto.AuthResponse;
import com.ajay.resume_analyzer.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
}
