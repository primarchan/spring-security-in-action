package org.example.ssiach15ex1as.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AuthServerConfig {

    @Value("${jwt.key}")
    private String jwtKey;

}
