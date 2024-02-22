package org.example.ssiach11ex1s2.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.ssiach11ex1s2.authentication.OtpAuthentication;
import org.example.ssiach11ex1s2.authentication.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    // 올바른 인증 논리를 적용하는 AuthenticationManager 자동 주입
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.signing.key}")
    private String signingKey;

    /**
     * 요청에 따라 올바른 인증을 요구하도록 doFilterInternal() 메서드 재정의
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        if (code == null) {  // HTTP 요청에 OTP 가 없으면, 사용자 이름과 암호로 인증해야 한다고 가정한다.
            Authentication authentication = new UsernamePasswordAuthentication(username, password);

            // UsernamePasswordAuthentication 의 인스턴스로 AuthenticationManager 를 호출한다.
            authenticationManager.authenticate(authentication);
        } else {
            Authentication authentication = new OtpAuthentication(username, code);
            authenticationManager.authenticate(authentication);

            SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

            // JWT 를 구축하고 인증된 사용자의 사용자 이름을 클레임 중 하나로 저장한다. 토큰을 서명하는 데 키를 이용했다.
            String jwt = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(key)
                    .compact();

            // 토큰을 HTTP 응답의 권한 부여 헤더에 추가한다.
            response.setHeader("Authorization", jwt);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // /login 경로에만 이 필터를 적용
        return !request.getServletPath().equals("/login");
    }

}
