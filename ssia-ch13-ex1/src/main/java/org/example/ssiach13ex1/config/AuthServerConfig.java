package org.example.ssiach13ex1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    // 컨텍스트 내에서 AuthenticationManager 인스턴스를 주입
    private final AuthenticationManager authenticationManager;

    // AuthenticationManager 를 설정하도록 configure() 메서드를 재정의
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    /*
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // ClientDetailsService 구현을 이용해 인스턴스 생성
        InMemoryClientDetailsService inMemoryClientDetailsService = new InMemoryClientDetailsService();

        // ClientDetails 인스턴스를 만들고, 클라이언트에 관한 필수 세부 정보 설정
        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setClientId("client");
        baseClientDetails.setClientSecret("secret");
        baseClientDetails.setScope(List.of("read"));
        baseClientDetails.setAuthorizedGrantTypes(List.of("password"));

        // InMemoryClientDetailsService 에 ClientDetails 인스턴스 추가
        inMemoryClientDetailsService.setClientDetailsStore(Map.of("client", baseClientDetails));

        // 예제 권한 부여 서버에서 이용할 수 있게 ClientDetailsService 구성
        clients.withClientDetails(inMemoryClientDetailsService);
    }
     */

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // ClientDetailService 구현을 이용해 메모리에 저장된 ClientDetails 관리
        // ClientDetails 의 인스턴스를 구축하고 추가
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("password")
                .scopes("read");
    }

}
