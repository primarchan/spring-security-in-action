package org.example.ssiach6ex1.config;

import lombok.RequiredArgsConstructor;
import org.example.ssiach6ex1.security.AuthenticationProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 1. 두 해싱 알고리즘을 위한 암호 인코던 객체 정의
 * 2. 인증 프로세스에 필요한 세부 정보를 저장하는 user 및 authority 테이블을 나타낼 JPA 엔티티 정의
 * 3. 스프링 데이터를 위한 JpaRepository 계약을 정의 (예제에서 사용자를 직접 참조하므로 UserRepository 로 선언)
 * 4. User JPA 엔티티에 대해 UserDetails 계약을 구현하는 데코레이터를 만듦 (책임 분리해서 만듦)
 * 5. UserDetailsService 계약을 구현. 이를 위해 JpaUserDetailsService 라는 클래스를 만듦.
 * 이 클래스는 데이터베이스에서 사용자 세부 정보를 가져오기 위해 3단계에서 만든 UserRepository 를 이용.
 * JpaUserDetailsService 가 사용자를 발견하면 4단계에서 정의한 데코레이터 구현으로서 이를 반환
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ProjectConfig {

    private final AuthenticationProviderService authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form.defaultSuccessUrl("/main", true))
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                .authenticationProvider(authenticationProvider);

        return http.build();
    }

}
