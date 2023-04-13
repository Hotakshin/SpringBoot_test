# SpringBoot_test

스프링부트 연습

## Spring Boot 란?

-   독립적이고, 실제 제품 수준의 애플리케이션을 쉽게 만들어준다.
-   특정 버전의 서드파티 라이브러리들을 특정한 방법으로 사용할 수 있도록 확장점을 제공한다.
-   최소한의 노력으로 시작할 수 있도록 틀을 만들어놓았다.
-   스프링 부트는 최소한의 설정만 필요하다. 그러나, 보안 관련 설정 등 추가적인 설정이 추가로 될 가능성이 크다.
-   java -jar를 이용하여 쉽게 실행 할 수있다. ( 큰 장점 중의 하나 )
-   war 로 묶어서 배포할 수 있다. 설치된 WAS에 배포한다.
-   커맨드 라인 툴도 제공한다.

## Spring Boot의 중요목적

-   손쉽게 프로젝트를 시작할 수 있도록 한다.
-   주관을 가지고 만들어진 제품이지만 요구사항이 다양해짐으로써 주어진 틀에서도 얼마든지 벗아날 수 있도록 한다. ( 커스터 마이징이 가능하도록 한다.)
-   프로덕션을 위한 모니터링, 헬스 체크등의 기능도 제공한다.
-   코드제네레이션을 제공하는 도구가 아니다. XML 설정은 필요없다.

## 스프링부트(2.7.5) SYSTEM 요구사항

-   자바 8~19 Spring 5.3.23 릴리즈 이상을 사용한다.
-   MAVEN 3.5+ , 그래들 6.8.x, 6.9.x, 7.x 에서 지원한다.
-   서블릿 컨테이너
    -   Tomcat 9.0 이상 서블릿 버전 4.0
    -   Jetty 9.4, 서블릿 버전 3.1
    -   Jetty 10.0, 서블릿 버전 4.0
    -   Undertow 2.0, 서블릿 버전 4.0
