package com.ajay.resume_analyzer.service.auth;

import com.ajay.resume_analyzer.dto.AuthResponse;
import com.ajay.resume_analyzer.dto.RegisterRequest;
import com.ajay.resume_analyzer.entity.Role;
import com.ajay.resume_analyzer.entity.User;
import com.ajay.resume_analyzer.exception.EmailAlreadyExistsException;
import com.ajay.resume_analyzer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return new AuthResponse("User registered successfully");
    }
}