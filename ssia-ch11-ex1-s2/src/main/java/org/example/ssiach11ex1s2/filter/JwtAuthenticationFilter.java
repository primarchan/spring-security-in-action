package org.example.ssiach11ex1s2.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ssiach11ex1s2.authentication.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

        // 토큰을 구문 분석해 클레임을 얻고 서명을 검증한다. 서명이 유효하지 않으면 예외가 던져진다.
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        String username = String.valueOf(claims.get("username"));

        // SecurityContext 에 추가할 Authentication 인스턴스를 만든다.
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
        UsernamePasswordAuthentication auth = new UsernamePasswordAuthentication(username, null, List.of(grantedAuthority));

        // SecurityContext 에 Authentication 객체를 추가한다.
        SecurityContextHolder.getContext().setAuthentication(auth);

        // 필터 체인의 다음 필터를 호출한다.
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // /login 경로에 대한 요청에는 트리거되지 않도록 이 필터를 구성한다.
        return request.getServletPath().equals("/login");
    }

}
