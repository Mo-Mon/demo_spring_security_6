package com.example.demo_spring_security.repository;

import com.example.demo_spring_security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    
}
