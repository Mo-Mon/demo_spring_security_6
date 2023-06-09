package com.example.demo_spring_security.config.security;

import com.example.demo_spring_security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final UserDetailsService userDetailsService;

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwtToken;
        String email;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        if(checkUrlPublic(request)){
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);

        email = jwtProvider.extractEmail(jwtToken);

        if(StringUtils.hasText(email) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtProvider.isTokenValid(jwtToken,userDetails) && tokenService.checkExpiredAndRevokeAccessToken(jwtToken)){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }else{
                // Nếu access token đã hết hạn, trả về HTTP status code 401 Unauthorized
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access token has expired");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Boolean checkUrlPublic(HttpServletRequest request){
        List<String> listUrl = List.of("/api/v1/auth");
        for (String url : listUrl) {
            if (request.getServletPath().contains(url)) {
                return true;
            }
        }
        return false;
    }
}
