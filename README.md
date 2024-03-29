# SPRING SECURITY in Action - Laurentiu Spilca, 2022

## 1장. 오늘날의 보안
- 스프링 시큐리티는 스프링 애플리케이션을 보호하기 위한 가장 인기있는 선택이며, 다양한 스타일과 아키텍처에 적용할 수 있는 갖가지 대안을 제공한다.
- 시스템의 계층별로 보안을 적용해야 하며, 계층별로 다른 관행을 이용해야 한다.
- 보안은 소프트웨어 프로젝트를 시작할 때부터 고려해야 하는 공통 관심사이다.
- 일반적으로 취약성를 예방하는 투자 비용보다 공격의 대가가 훨씬 크다.
- 오픈 웹 애플리케이션 보안 프로젝트(OWASP) 는 취약성과 보안 과련 ㅎ사항을 참고할 수 있는 훌륭한 장소다.
- 종종 아주 작은 실수 때문에 큰 피해가 발생한다. 예를 들어 로그나 오류 메시지에 민간한 데이터를 노출하는 것은 애플리케이션에 취약성을 만드는 흔한 실수다.

## 2장. Hello! Spring security
- 스프링 시큐리티를 애플리케이션의 종속성으로 추가하면 스프링 부트가 약간의 기본 구성를 제공한다.
- 인증과 권한 부여를 위한 기본 구성요소인 `UserDetailService`, `PasswordEncoder`, `AuthenticationProvider` 를 구현했다.
- `User 클래스`로 사용자를 정의할 수 있다. 사용자는 사용자 이름, 암호, 권한을 가져야 한다. 권한은 사용자가 애플리케이션의 컨텍스트에서 수행할 수 있는 작업을 지정한다.
- 스프링 시큐리티는 `UserDetailService` 의 간단한 구현인 `InMemoryUserDetailsManager` 를 제공한다. `UserDetailsService` 의 인스턴스와 같은 사용자를 추가해서 애플리케이션의 메모리에서 사용자를 관리할 수 있다.
- `NoOpPasswordEncoder` 는 `PasswordEncoder` 계약을 구현하며, 암호를 일반 텍스트로 처리한다. 이 구현은 학습 예제와 개념 증명에 적합하지만, 운영 단계 애플리케이션에는 적합하지 않다.
- `AuthenticationProvider` 계약을 이용해 애플리케이션의 맞춤형 인증 논리를 구현할 수 있다.
- 구성을 작성하는 방법은 여러 가지가 있지만, 한 애플리케이션에서는 한 방법을 선택하고 고수해야 코드를 깔끔하고 이해하기 쉽게 만들 수 있다.

## 3장. 사용자 관리
- `UserDetails` 인터페이스 스프링 시큐리티에서 사용자를 기술하는 데 이용되는 계약이다.
- `UserDetailsService` 인터페이스는 애플리케이션이 사용자 세부 정보를 얻는 방법을 설명하기 위해 스프링 시큐리티의 인증 아키텍처에서 구현해야 하는 계약이다.
- `UserDetailManager` 인터페이스는 `UserDetailService` 를 확장하고, 사용자 생성, 변경, 삭제와 관련된 동작을 추가한다.
- 스프링 시큐리티는 `UserDetailManager` 계약의 여러 구현을 제공한다. 이러한 구현에는 `InMemoryUserDetailManager`, `JdbcUserDetailManager`, `LdapUserDetailManager` 가 있다.
- `JdbcUserDetailManager` 는 JDBC 를 직접 이용하므로 애플리케이션이 다른 프레임워크에 고정되지 않는다는 이점이 있다.

## 4장. 암호 처리
- `PasswordEncoder` 는 인증 논리에서 암호를 처리하는 가장 중요한 책임을 담당한다.
- 스프링 시큐리티는 해싱 알고리즘에 여러 대안을 제공하므로 필요한 구현을 선택하기만 하면 된다.
- 스프링 시큐리티 `암호화 모듈(SSCM)` 에는 키 생성기와 암호기를 구현하는 여러 대안이 있다.
- 키 생성기는 암호화 알고리즘에 이용되는 키를 생성하도록 도와주는 유틸리티 객체이다.
- 암호기는 데이터 암호화와 복호화를 보와주는 유틸리티 객체이다.

## 5장. 인증 구현
- `AuthenticationProvider` 구성 요소를 이용하면, 맞춤형 인증 논리를 구현할 수 있다.
- 맞춤형 인증 논리를 구현할 때는 책임을 분리하는 것이 좋다. `AuthenticationProvider` 는 사용자 관리는 `UserDetailsService` 에 위임하고 암호 검증 책임은 `PasswordEncoder` 에 위임한다.
- `SecurityContext` 는 인증이 성공한 후, 인증된 엔티티에 대한 세부 정보를 유지한다.
- `SecurityContext` 를 관리하는 데는 `MODE_THREADLOCAL`, `MODE_INHERITABLETHREADLOCAL`, `MODE_GLOBAL` 의 세가지 전략을 이용할 수 있으며, 선택한 전략에 따라 다른 스레드에서 `SecurityContext` 세부 정보에 접근하는 방법이 달라진다.
- 공유 스레드 로컬 전략을 사용할 때는, 스프링이 관리하는 스레드에만 전략이 적용된다는 것을 기억하자. 프레임워크는 자신이 관리하지 않는 스레드에는 `SecurityContext` 를 복사하지 않는다.
- 스프링 시큐리티는 코드에서 생성했지만, 프레임워크가 인식한 스레드를 관리할 수 있는 우수한 유틸리티 클래스를 제공한다. 코드에서 생성한 스레드의 `SecurityContext` 를 관리하기 위해 다음 클래스를 이용할 수 있다.
  - `DelegatingSecurityContextRunnable`
  - `DelegationSecurityContextCallable`
  - `DelegationSecurityContextExecutor`
- 스프링 시큐리티는 양식 기반 로그인 인증 메서드인 `formLogin()` 으로 로그인 양식과 로그아웃하는 옵션을 자동으로 구성한다. 작은 웹 애플리케이션의 경우 직관적으로 이용할 수 있다.
- `formLogin()` 인증 메서드는 세부적으로 맞춤 구성이 가능하며, `HTTP Basic 방식`과 함께 이용해 두인증 유형을 모두 지원할 수도 있다.

## 6장. 실전: 작고 안전안 웹 애플리케이션
- 실제 애플리케이션에서는 같은 개념의 다른 구현이 필요한 종속성이 흔하게 이용된다. 이 예제에서는 스프링 시큐리티의 `UserDetails` 와 `JPA` 구현의 `User 엔티티`가 이러한 사례에 해당하며, 이때는 책임을 다른 클래스로 분리해 가독성을 높이는 것이 좋다.
- 대부분은 같은 기능을 여러 가지 다른 방법으로 구현할 수 있으며, 가장 단순한 해결책을 선택해야 한다. 코드를 이해하기 쉽게 만들면 오류와 보안 침해의 여지가 줄어든다.

## 7장. 권한 부여 구성: 엑세스 제한
- 권한 부여는 애플리케이션이 인증된 요청을 허가할지 결정하는 프로세스다. 권한 부여는 항상 인증 이후에 수행된다.
- 애플리케이션이 인증된 사용자의 권한과 역할에 따라 권한을 부여하는 방법을 구성할 수 있다.
- 애플리케이션이 인증되지 않은 사용자가 특정 요청을 수행할 수 있게 지정할 수도 있다.
- `denyAll()` 메서드로 앱이 모든 요청을 거부하고 `permitAll()` 메서드로 모든 요청을 수락하게 할 수 있다.

## 8장. 권한 부여 구성: 제한 적용
- 실제 시나리오에서는 요청마다 다른 권한 부여 규칙을 적용하는 경우가 많다.
- 경로와 `HTTP 방식`에 따라 권한 부여 규칙을 구성할 요청을지정한다. 이를 위해 MVC, 앤트, 정규식의  세 가지 선택기 매서드를 이용하는 방법을 배운다.
- `MVC` 와 `앤트` 선택기는 서로 비슷하며 일반적으로 이 중 하나를 선택해 권한 부여 제한을 적용할 요청을 지정할 수 있다.
- 요구 사항이 `앤트`나 `MVC` 식으로 해결하기에 너무 복잡할 때는 이보다 강력한 정규식으로 구현할 수 있다.
  - 정규식은 읽기 어렵고 상당히 길어질 수 있으므로 마지막 수단으로 남겨 두는 것이 좋다. `MVC` 와 `앤트` 식으로 문제를 해결할 수 없을 때만 정규식을 이용하자.

## 9장. 필터 구현
- 웹 애플리케이션의 첫 번째 계층은 `HTTP 요청`을 가로채는 필터 체인이다. 스프링 시큐리티 아키텍처의 다른 구성 요소는 요구 사항에 맞게 맞춤 구성할 수 있다.
- 필터 체인에서 기존 필터 위치 또는 앞이나 뒤에 새 필터를 추가해 필터 체인을 맞춤 구성할 수 있다.
- 기존 필터와 같은 위치에 여러 필터를 추가할 수 있으며, 이 경우 필터가 실행되는 순서는 정해지지 않는다.
- 필터 체인을 변경하면, 애플리케이션의 요구 사항에 맞게 인증과 권한 부여를 맞춤 구성하는 데 도움이 된다.

## 10장. CSRF 보호와 CORS 적용
- `CSRF(Cross Site Request Forgery, 사이트 간 요청 위조)` 는 사용자를 속여 위조 스크립트가 포함된 페이지에 접근하도록 하는 공격 유형이다. 이 스크립트는 애플리케이션에 로그인한 사용자를 가장해 사용자 대신 작업을 실행할 수 있다.
- `CSRF 보호`는 스프링 시큐리티에서 기본적으로 활성화된다.
- 스프링 시큐리티 아키텍처에서 `CSRF 보호` 논리의 진입점은 `HTTP 필터`이다.
- `CORS(Cross Origin Resource Sharing, 교차 출처 리소스 공유)` 는 특정 도메인에서 호스팅되는 웹 애플리케이션이 다른 도메인의 콘텐츠에 접근하려고 할 때 발생하며, 기본적으로 브라우저는 이러한 접근을 허용하지 않는다. CORS 구성을 이용하면 리소스의 일부를 브라우저에 실행되는 웹 애플리케이션의 다른 도메인에서 호출할 수 있다.
- `CORS` 를 구성하는 방법에는 `@CrossOrigin 어노테이션`으로 엔드포인별로 구성하는 방법과 `HttpSecurity 객체`의 `cors() 메서드`로 중앙화된 구성 클래스에서 구성하는 방법이 있다.

## 11장. 실전: 책임의 분리
- 맞춤형 인증과 권한을 부여할 때는 항상 스프링 시큐리티에 있는 계약을 이용한다. 이러한 계약에는 `AuthenticationProvider`, `AuthenticationManager`, `UserDetailService` 등이 있다. 이 방식을 따르면 아키텍처를 이해하기 쉽게 구축하고 애플리케이션의 오류 발생 가능성이 줄어든다.
- 토큰은 사용자를 위한 식별자이며, 생성한 후 서버가 이식할 수 있으면 어떤 구현이든 이용할 수 있다. 실제 토큰의 사례에는 출입 카드, 티켓 또는 박물관에 들어갈 때 받는 스티커 등이 있다.
- `UUID(Universally Unique Identifier, 범용 고유 식별자)` 처럼 단순한 값을 토큰으로 이용할 수 있지만, `JWT(JSON Web Token)` 로 구현된 토큰이 더 자주 이용된다. `JWT` 는 교환되는 데이터를 요청에 저장하고, 전송 중에 변경되지 않도록 서명할 수 있는 등 여러 장점을 제공한다.
- `JWT 토큰` 은 서명하거나 완전히 암호호할 수 있다. 서명된 JWT 토큰을 `JWS(JSON Web Token Signed)` 라고 하며, 세부 정보과 완전히 암호화된 토큰을 `JWE(JSON Web Token Encrypted)` 라고 한다.
- `JWT` 에는 너무 많은 세부 정보를 저장하지 않는 것이 좋다. 토큰이 서명되거나 암호화되면 토큰이 길수록 이를 서명하거나 암호화하는데 시간이 많이 걸린다. 또한 토큰은 `HTTP 요청`에 헤더로 보낸다는 것을 기억하자. 토큰이 길면 각 요청에 추가되는 데이터가 증가하고 애플리케션의 성능이 영향을 크게 받는다.
- 애플리케이션을 유지 관리하고 확장하기 편하도록 책임을 분리하는 것이 좋다. 따라서 이 실전 예제에서는 인증을 인증 서버라고 하는 별도의 앱으로 분리했다. 클라이언트를 지원하는 백앤드 애플리케이션은 비지니스 논리 서버로 분리 했고, 클라이언트를 인증할 때는 별도의 인증 서버를 이용했다. 
- `다단계(다중) 인증(MFA, Multi-Factor Authentication)` 은 사용자가 리소스에 접근할 때 여러 번 다른 방법으로 인증하도록 요청하는 인증 전략이다. 이 예에서 사용자는 사용자 이름과 암호로 인증한 후 SMS 메세지를 통해 바든 `OTP` 를 제시하여 특정 전화번호를 가지고 있음을 입증해야했다. 이 방식으로 자격 증명 도난으로부터 사용자의 리소스를 더 잘 보호할 수 있다.
- 한 가지 문제에 대한 좋은 해결책이 두 개 이상인 경우가 많다. 항상 가능한 모든 해결책을 고려하고 시간이 허용된다면 모든 옵션의 개념 증명을 구현해 시니리오에 가장 적합한 옵션을 찾는 것이 좋다.

## 12장. OAuth 2.0: 이 작동하는 방법
- `OAuth 2.0 프레임워크`는 엔티티가 사용자 대신 리소스에 접근할 수 있게 하는 방법을 기술한다. 애플리케이션에 `OAuth 2.0 프레임워크`를 이용해 인증 및 권한 부여 논리를 구현할 수 있다.
- 애플리케이션이 엑세스 토큰을 엳기 위해 사용할 수 있는 다양한 흐름을 `Grant`라고 한다. 시스템 아키텍처에 따라 적합한 `Grant 유형`을 선택해야 한다.
  - `승인 코드 Grant 유형`은 사용자가 직접 권한 부여 서버에서 인증하여 클라이언트가 엑세스 토큰을 얻을 수 있게 해준다. 이 `Grant 유형`은 사용자가 클라이언트를 신뢰하지 않고, 클라이언트와 자격 증명을 공유하기를 원하지 않을 때 적합하다.
  - `암호 Grant 유형`에서는 사용자가 자신의 자격 증명을 클라이언트와 공유한다고 가정한다. 클라이언트를 신뢰할 수 있는 경우에만 이 유형을 적용해야 한다.
  - `클라이언트 자격 증명 Grant 유형`은 클라이언트가 자격 증명으로만 인증하여 토큰을 얻는다는 의미다. 클라이언트가 사용자의 리소스 아닌 리소스 서버의 엔드포인트를 호출해야 할 때, 이 `Grant 유형`을 선택한다.
- 스프링 시큐리티는 OAuth 2.0 프레임워크를 지원해서 애플리케이션에서 몇 줄의 코드로 프레임워크를 구성할 수 있다.
- 스프링 시큐리티는 `ClientRegistration` 의 인스턴스를 이용해 권한 부여 서버에서 클라이언트의 등록을 나타낸다.
- `스프링 시큐리티 OAuth 2.0` 구현에서 특정 클라이언트 등록을 찾는 책임이 있는 구성 요소를 `ClientRegistrationRepository` 라고 한다. 스프링 시큐리티로 `OAuth 2.0 클라이언트`를 구현할 때는, 사용 가능한 `ClientRegistration` 이 하나 이상 있는 `ClientRegistrationRepository` 객체를 정의해야 한다.

## 13장. OAuth 2.0: 권한 부여 서버 구현
- `ClientRegistration 인터페이스`는 스프링 시큐리티에서 `OAuth 2.0 클라이언트` 등록을 정의한다. `ClientRegistrationRepository 인터페이스`는 클라이언트 등록 관리를 담당하는 객체를 기술한다. 이 두 계약으로 권한 부여 서버가 클라이언트 등록을 관리하는 방법을 맞춤 구성할 수 있다.
- 스프링 시큐리티로 구현한 권한 부여 서버에서는 클라이언트 등록으로 `Grant 유형`이 정해진다. 같은 권한 부여 서버가 다른 클라이언트에 다른 `Grant 유형`을 제공할 수 있다. 여러 `Grant 유형`을 정의하기 위해 권한 부여 서버에서 특정한 것을 구현할 필요가 없다는 의미이다.
- `승인 코드 Grant 유형`에서 권한 부여 서버는 사용자에게 로그인할 방법을 제공해야 한다. 이는 승인 코드 흐름에서 사용자(리소스 소유자)가 권한 부여 서버에 직접 인증하여 클라이언트에 엑세스 권한을 부여해야 하기 때문이다.
- 하나의 `ClientRegistration` 이 여러 `Grant 유형`을 요청할 수 있따. 얘를 들어 한 클라이언트가 다른 상황에서 `암호 Grant 유형`과 `승인 코드 Grant 유형`을 모두 이용할 수 있다.
- `클라이언트 자격 증명 Grant 유형`은 백엔드-대-백엔드 권한 부여에 이용한다. 클라이언트가 `클라이언트 자격 증명 Grant 유형`과 함께 다른 `Grant 유형`을 요청하는 것은 기술적으로 가능하자만 흔하지 않다.
- 갱신 토큰은 `승인 코드 Grant 유형` 및 `암호 Grant 유형`과 함께 이용할 수 있다. 클라이언트 등록에 갱신 토큰을 추가해서 권한 부여 서버가 액세스 토큰과 함께 갱신 토큰을 발행하도록 지시할 수 있다. 갱신 토큰을 이용하면 클라이언트가 사용자를 다시 인증할 필요 없이 새 엑세스 토큰을 얻을 수 있다.

## 14장. OAuth 2.0: 리소스 서버 구현
- 리소스 서버는 사용자 리소스를 관리하는 스프링 구성 요소다.
- 리소스 서버는 권한 부여 서버가 클라이언트를 위해 발행한 토큰을 검증할 방법이 필요하다.
- 리소스 서버가 토큰을 검증하는 한 가지 옵션은 권한 부여 서버를 직접 호출하는 것이다. 이 방식은 권한 부여 서버에 너무 심한 부담을 줄 수 있다. 개인적으로는 이 방식을 피하는 편이다.
- 리소스 서버가 토큰을 검증할 수 있도록 데이터베이스 참조 아키텍처를 구현할 수 있다. 이 구현에서는 권한 부여 서버와 리소스 서버가 토큰을 관리하는 같은 데이터베이스에 접근한다.
- 데이터베이스 참조는 리소스 서버와 권한 부여 서버 간의 종속성을 없앤다는 장점이 있다. 하지만 토큰을 지속하는 데이터베이스를 추가해야 하며, 대량의 요청을 처리하면 이 데이터베이스가 병목 지점이 돼서 시스템 성능을 저하시킬 수 있다.
- 토큰 관리를 구현하려면 `TokenStore 형식의 객체`를 이용해야 한다. TokenStore 의 구현을 직접 작성할 수도 있지만, 대부분 스프링 시큐리티에 있는 구현을 이용한다.
- `JdbcTokenStore` 는 데이터베이스에 액세스 토큰오가 갱신 토큰을 지속하는 데 사용할 수 있는 `TokenStore 구현`이다.

## 15장. OAuth 2.0: JWT 와 암호화 서명 사용
- 오늘날 애플리케이션은 `OAuth 2.0 아키텍처의 토큰`을 검증하기 위해 암호화 서명을 이용하는 경우가 많다.
- 암호화 서명을 통한 토큰 검증에서 가장 널리 이용되는 토큰 구현은 `JWT(JSON Web Token)` 이다.
- 대칭 키로 토큰을 서명하고 서명을 검증할 수 있다. 대칭 키를 이용하면 간단하게 구현할 수 있지만, 권한 부여 서버가 리소스 서버를 신뢰하지 않는 상황에는 맞지 않다.
- 구현에 대칭 키가 적합하지 않을 때는 비대칭 키 쌍을 이용한 토큰 서명과 검증을 구현할 수 있다.
- 시스템을 키 도난에 덜 취약하게 만들려면, 키를 정기적으로 변경하는 것이 좋다. 키를 정기적으로 변경하는 일을 키 순환이라고 한다.
- 리소스 서버 쪽에서 직접 공개 키를 구성할 수 있다. 이 방식은 간단하지만 키 순환이 어려워진다.
- 키 순환을 간단하게 하기 위해 권한 부여 쪽에서 키를 구성하고, 리소스 서버가 특정 엔드포인트에서 키를 얻게 할 수 있다.
- 구현 요구 사항에 필요할 때는 토큰 본문에 세부 정보를 추가하도록 `JWT` 를 맞춤 구성할 수 있다. 권한 부여 서버는 맞춤형 세부 정보를 토큰 본문에 추가하고 리소스 서버는 이러한 세부 정보를 권한 부여에 이용한다.

## 16장. 전역 메서드 보안: 사전 및 사후 권한 부여
- 스프링 시큐리티로 엔드포인트 수준만이 아닌 애플리케이션의 어떤 계층에도 권한 부여 규칙을 적용할 수 있다. 이를 위해서는 전역 메서드 보안 기능을 활성화해야 한다.
- 전역 메서드 보안 기능은 기본적으로 비활성화되며, 활성화하려면 애플리케이션의 구성 클래스에 `@EnableGlobalMethodSecurity 어노테이션`을 지정해야한다.
- 애플리케이션이 메서드를 호출하기 전에 검사하는 권한 부여 규칙을 적용할 수 있다. 이러한 권한 부여 규칙이 준수되지 않으면 프레임워크는 메서드 실행을 허용하지 않는다. 메서드를 호출하기 전에 권ㄴ한 부여 규칙을 확인하는 것을 사전 권한 부여라고 한다.
- 사전 권한 부여를 구현하려면 권한 부여 규칙을 정의하는 `SpEL 식`과 함께 `@PreAuthorize 어노테이션`을 이용한다.
- 메서드를 호출한 후, 호출자가 반환된 값을 이용하고 실행 흐름을 걔속할 수 있을지 결정하려면 사후 권한 부여을 이용한다.
- 복잡한 권한 부여 논리를 구현할 때는, 코드를 읽기 쉽게 작성할 수 있게 이 논리를 다른 클래스로 분리해야 한다. 스프링 시큐리티에서 이를 위한 일반적인 방법은 `PermissionEvaluator` 를 구현하는 것이다.
- 스프링 시큐리티는 `@RolesAllowed` 및 `@Secured 어노테이션`과 같은 이전 사양과의 호환성을 지원한다. 원한다면 이러한 이전 어노테이션을 이용해도 되지만 `@PreAuthorize` 및 `@PostAuthorize` 보다 기능이 떨어지며 실제 스프링 시나리오에서 사용되는 일은 거의 없다.

## 17장. 전역 메서드 보안: 사전 및 사후 필터링
- 필터링은 프레임워크와 메서드의 입력 매개 변수나 메서드의 반환 값을 검증하여 제시된 기준을 충족하지 않은 요소를 필터링하는 권한 부여 방식이며, 메서드 실행 자체를 제한하지 않고 메서드의 입력과 출력 값을 필터링하는 데 중점을 둔다.
- 필터링을 이용하면, 메서드가 처리하도록 승인된 값만 받고 메서드 호출자에게 적절하지 않은 값은 반환하지 않게 할 수 있다.
- 필터링은 메서드 호출을 제한하지는 않지만, 메서드의 매개 변수로 보낼 수 있는 데이터와 메서드가 반환하는 데이터를 제한할 수 있다.이 방삭으로 메서드의 입출력을 제어할 수 있다.
- 메서드의 매개 변수로 보낼 값을 제한하려면 `@PreFilter 어노테이션`을 이용한다. `@PreFilter 어노테이션`은 메서드의 매개 변수로 보낼 수 있는 값에 대한 조건을 받는다. 프레임워크 매개 변수로 제공된 컬렉션에서 주어진 규칙을 따르지 안흔 모든 값을 필터링한다.
- `@PreFilter 어노테이션`을 이용하려면, 메서드의 매개 변수가 컬렉션이거나 배열이어야 한다. 어노테이션에서 규칙을 정의하는 SpEL 식에서 컬렉션 내의 객체를 참조하려면 `filterObject` 를 이용한다.
- 메서드가 반환되는 값을 제한하려면 `@PostFilter 어노테이션`을 적용할 수 있다.`@PostFilter 어노테이션`을 이용하려면 메서드의 반환 형식이 컬렉션이거나 배열이어야 한다. `@PostFilter` 를 적용하는 것은 성능 면에서 그리 좋은 선택이 아니다. 성능 문제를 방지하려면 결과에 대한 필터링을 데이터베이스 수준에 직접 적용해야 한다.
- 스프링 시큐리티는 스프링 데이터와 손쉽게 통합할 수 있으며 이 점을 활용해 스프링 데이터 리포지토리의 메서드에 `@PostFilter` 를 적용하는 것을 피해야 한다.

## 18장. 실전: OAuth 2.0 애플리케이션
- 개발자가 반드시 맞춤형 권한 부여 서버를 직접 구현해야 하는 것은 아니며, 실제 시나리오에서는 `Keycloak` 과 같은 툴을 권한 부여 서버로 이용할 때가 많다.
- `Keycloak` 은 `오픈 소스 ID` 및 액세스 관리 솔루션이며 사용자 관리와 권한 부여를 처리하는 탁월한 유연성을 제공한다. 맞춤형 솔루션을 직접 구현하기보다 기존의 툴을 이용하는 것이 나을 수 있다.
- `Keycloak` 과 같은 솔루션이 있어도 권한 부여를 위한 맞춤형 솔루션을 절대 구현하지 않는다는 의미는 아니다. 실제 시나리오에서는 개발할 애플리케이션의 이해 관계자가 타사의 구현을 신뢰하자 않는 경우가 있으므로 가능한 모든 시나리오에 대비할 필요가 있다.
- `OAuth 2.0 프레임워크` 를 통해 구현된 시스템에서 전역 메서드 보안을 사용할 수 있다. 이러한 시스템은 리소스 서버 수준에서 전역 메서드 보안 제한을 구현하여 리소스를 보호한다.
- `SpEL 식`에서 특적 `OAuth 2.0 요소`를 이용해 권한 부여를 수행할 수 있다. 이러한 `SpEL 식`을 작성하려면 식을 해석할 수 있게 `OAuth2WebSecurityExpressionHandler` 를 구성해야 한다.

##  19장. 리액티브 앱을 위한 스프링 시큐리티
- 리액티브 애플리케이션은 데이터를 처리하고 다른 구성 요소 간에 메시지를 교환하는 다른 스타일을 이용한다, 리액티브 앱은 데이터를 작은 세그먼트로 분할하고 처리 및 교환하는 일부 상황에 더 나은 선택이다.
- 다른 모든 애플리케이션과 마찬가지로 보안 구성을 이용해 리액티브 앱을 보호해야 한다. 스프링 시큐리티에는 비리액티브 앱과 마찬가지로 리액티브 앱에 보안 구성을 적용하기 위한 탁월한 툴 세트가 있다.
- 리액티브 앱에서 스프링 시큐리티로 사용자 관라를 구현하려면 `ReactiveUserDetailService` 계약을 이용한다. 이 구성요소는 비리액티브 앱의 `UserDetailsService` 와 마찬가지로 앱에 사용자 세부 정보를 얻는 방법을 알려준다.
- 리액티브 웹 애플리케이션에 엔드포인트 권한 부여 규칙을 구현하려면 `SecurityWebFilterChain` 형식의 인스턴스를 만들고 이를 스프링 컨텍스트에 추가해야 한다. `SecurityWebFilterChain` 인스턴스는 `ServerHttpSecurity 빌더` 로 만든다.
- 일반적으로 권한 부여 구성을 정의하는 메서드의 이름은 비리액티브 앱에 이용하던 메서드의 이름과 같지만, 리액티브 용어와 관련한 약간의 차이가 있다. 예를 들어, 리액티브 앱에는 `authorizeRequests()` 가 아닌 `authorizeExchange()` 를 이용한다.
- 스프링 시큐리티에는 메서드 수준에 권한 부여 규칠을 정의할 수 있는 리액티브 메서드 보안이라는 기능도 있으며, 이를 이용하면 리액티브 앱의 모든 걔층에 유연하게 권한 부여 규칙을 적용할 수 있다. 이 기능은 비리액티브 앱의 전역 메서드 보안과 비슷하다.
- 하지만 아직 리액티브 메서드 보안은 전역 메서드 보안만큼 성숙한 구현이 아니다. `@PreAuthorize` 및 `@PostAuthorize 어노테이션`은 현재 이용 가능하지만, 아직 `@PreFilter` 및 `@PostFilter` 개발을 기다리고 있다.

## 20장. 스프링 시큐리티 테스트
- 테스트를 작성하는 일은 기본적인 관행이며, 새 구현이나 기존 기능을 망가뜨리지 않는지 확인하기 위해 테스트를 작성해야 한다.
- 자신의 코드를 테스트하는 것은 물론 이용하는 라이브러리 및 프레임워크와도 통합도 테스트해야 한다.
- 스프링 시큐리티는 보안 구성을 테스트하기 위한 훌륭한 지원을 제공한다.
- 모의 사용자로 직접 권한 부여를 테스트할 수 있다. 일반적으로 인증 테스트보다 권한 부여 테스트를 더 많이 해야 하므로 인증을 제외하고 권한 부여를 위한 별도의 테스트를 작성한다.
- 수가 적은 인증 테스트를 별도로 수행하고 엔드포인트와 메서드에 대한 권한 부여 구성을 테스트하는 것이 시간을 절약하는 데 도움이 된다.
- 스프링 시큐리티는 비리액티브 앱 엔드포인트의 보안 구성에 대한 테스트를 작성하기 위해 `MockMvc` 라는 우수한 보조 기능을 제공한다.
- 또한 리액티브 앱 엔드포인트의 보안 구성에 대한 테스트를 작성하기 위해 `WebTestClient` 라는 우수한 보조 기능을 제공한다.
- 메서드 보안을 이용하면, 보안 구성을 작성한 메서드에 대한 테스트를 직접 작성할 수 있다.