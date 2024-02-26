package org.example.ssiach12ex1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = clientRegistration();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Login();

        http
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated());

        return http.build();
    }

    // 나중에 반환되는 ClientRegistration 을 얻기 위해 이 메서드를 호출
    private ClientRegistration clientRegistration() {
        // 스프링 시큐리티가 깃허브 공통 공급자를 위해 제공하는 구성에서 시작
        // 해당 URI 가 이미 설정돼 있으므로 깃허브 공급자 선택
        return CommonOAuth2Provider.GITHUB
                // 클라이언트 등록을 위한 ID 제공
                .getBuilder("github")
                // 클라이언트 자격 증명 제공 (clientId, clientSecret)
                .clientId("a7553955a0c534ec5e6b")
                .clientSecret("1795b30b425ebb79e424afa51913f1c724da0dbb")
                .build();
    }



}
