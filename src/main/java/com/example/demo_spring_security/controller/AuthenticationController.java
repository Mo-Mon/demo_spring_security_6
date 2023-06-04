package com.example.demo_spring_security.controller;

import com.example.demo_spring_security.payload.request.AuthenticationRequest;
import com.example.demo_spring_security.payload.request.RegisterUserRequest;
import com.example.demo_spring_security.payload.response.AuthenticationResponse;
import com.example.demo_spring_security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterUserRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/show")
    public ResponseEntity<?> show(
            @RequestBody RegisterUserRequest request
    ) {
        return ResponseEntity.ok("hello test link");
    }

}
