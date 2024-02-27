package org.example.ssiach18ex1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig {

    @Value("${claim.aud}")
    private String claimAud;

    @Value("${jwkSetUri}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.DELETE, "/**").hasAuthority("fitnessadmin")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                .decoder(jwtDecoder())
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }

    @Bean
    public NimbusJwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }


}
