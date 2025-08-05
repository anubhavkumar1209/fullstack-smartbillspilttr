package com.smartbillsplittr.service.impl;

import com.smartbillsplittr.dto.request.LoginRequest;
import com.smartbillsplittr.dto.request.SignupRequest;
import com.smartbillsplittr.dto.response.JwtResponse;
import com.smartbillsplittr.dto.response.MessageResponse;
import com.smartbillsplittr.exception.BadRequestException;
import com.smartbillsplittr.model.User;
import com.smartbillsplittr.repository.UserRepository;
import com.smartbillsplittr.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service                     // ← MUST be present
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MessageResponse register(SignupRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email is already in use!");
        }

        User u = new User();
        u.setUsername(req.getUsername());
        u.setFullName(req.getFullName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(u);

        return new MessageResponse("User registered successfully!");
    }

    @Override
    public JwtResponse authenticate(LoginRequest req) {
        /* your login code here */
        return null;
    }
}

