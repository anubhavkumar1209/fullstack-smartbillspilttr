package com.smartbillsplittr.controller;

import com.smartbillsplittr.dto.request.LoginRequest;
import com.smartbillsplittr.dto.request.SignupRequest;
import com.smartbillsplittr.dto.response.JwtResponse;
import com.smartbillsplittr.dto.response.MessageResponse;
import com.smartbillsplittr.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")  // Changed from "/api/auth" to just "/auth"
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest request) {
        try {
            MessageResponse msg = authService.register(request);
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        } catch (Exception e) {
            MessageResponse errorMsg = new MessageResponse("Registration failed: " + e.getMessage());
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        try {
            JwtResponse jwt = authService.authenticate(request);
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("AuthController is working!");
    }
}
