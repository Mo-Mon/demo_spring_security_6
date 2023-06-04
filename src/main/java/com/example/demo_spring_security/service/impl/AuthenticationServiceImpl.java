package com.example.demo_spring_security.service.impl;

import com.example.demo_spring_security.config.security.JwtProvider;
import com.example.demo_spring_security.entity.Role;
import com.example.demo_spring_security.entity.User;
import com.example.demo_spring_security.payload.request.AuthenticationRequest;
import com.example.demo_spring_security.payload.request.RegisterUserRequest;
import com.example.demo_spring_security.payload.response.AuthenticationResponse;
import com.example.demo_spring_security.repository.RoleRepository;
import com.example.demo_spring_security.repository.UserRepository;
import com.example.demo_spring_security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    @Override
    public AuthenticationResponse register(RegisterUserRequest request){
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles().stream().map( role -> roleRepository.findByName(role)).collect(Collectors.toSet()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtProvider.generateToken(new HashMap<>(), user);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        var jwtToken = jwtProvider.generateToken(new HashMap<>(), user);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }

}
