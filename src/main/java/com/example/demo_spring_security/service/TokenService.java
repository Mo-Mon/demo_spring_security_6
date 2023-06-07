package com.example.demo_spring_security.service;

import com.example.demo_spring_security.entity.Token;
import com.example.demo_spring_security.entity.User;

public interface TokenService {
    void saveTokenForUser(String jwtToken, User user, Boolean isRefresh);

    void revokeAllAccessTokenByUser(User user);

    void revokeAllTokenByUser(User user);

    Boolean checkExpiredAndRevokeRefreshToken(String jwtToken);

    Boolean checkExpiredAndRevokeAccessToken(String jwtToken);

    Token findByToken(String token);

    void closeToken(Token token);
}
