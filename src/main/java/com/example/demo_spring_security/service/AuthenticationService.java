package com.example.demo_spring_security.service;

import com.example.demo_spring_security.payload.request.AuthenticationRequest;
import com.example.demo_spring_security.payload.request.RegisterUserRequest;
import com.example.demo_spring_security.payload.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterUserRequest request);

    void logoutUserAllDevice(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(HttpServletRequest request,
                                        HttpServletResponse response) throws IOException;
}
