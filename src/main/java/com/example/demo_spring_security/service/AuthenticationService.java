package com.example.demo_spring_security.service;

import com.example.demo_spring_security.payload.request.AuthenticationRequest;
import com.example.demo_spring_security.payload.request.RegisterUserRequest;
import com.example.demo_spring_security.payload.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterUserRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
