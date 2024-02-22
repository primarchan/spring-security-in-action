package org.example.ssiach11ex1s2.config;

import lombok.RequiredArgsConstructor;
import org.example.ssiach11ex1s2.authentication.OtpAuthenticationProvider;
import org.example.ssiach11ex1s2.authentication.UsernamePasswordAuthenticationProvider;
import org.example.ssiach11ex1s2.filter.InitialAuthenticationFilter;
import org.example.ssiach11ex1s2.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final InitialAuthenticationFilter initialAuthenticationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final OtpAuthenticationProvider otpAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated());

        return http.build();
    }

}
