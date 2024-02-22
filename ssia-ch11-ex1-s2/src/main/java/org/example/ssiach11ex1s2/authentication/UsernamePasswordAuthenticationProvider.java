package org.example.ssiach11ex1s2.authentication;

import lombok.RequiredArgsConstructor;
import org.example.ssiach11ex1s2.proxy.AuthenticationServerProxy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationServerProxy authenticationServerProxy;

    /**
     * 프록시로 인증 서버를 호출한다. SMS 를 통해 클라이언트에 OTP 를 보낸다.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        authenticationServerProxy.sendAuth(username, password);

        return new UsernamePasswordAuthenticationToken(username, password);
    }


    /**
     * Authentication 의 UsernamePasswordAuthentication 형식을 지원할 이 AuthenticationProvider 를 설계한다.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
    }

}
