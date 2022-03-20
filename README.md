# 프로젝트 목표🚀🚀🚀
프로젝트를 구현함으로써 기능 구현을 목표로 하기 보다는 DDD, Multi Module Project, BFF 구조를 
프로젝트에 녹여내고자 합니다. 특히 DDD, Hexagonal Architecture의 경우 추상적이고 광범위한 내용이지만 
코드를 통해 구체화하고 개념을 익히는 것이 프로젝트의 본 목적입니다.


## 개발자🚀🚀
        		
BE Developer : ensu6788@gmail.com 
            
FE Developer : [FE Developer github](https://github.com/roum02/vanilla-login)




## 기능🚀🚀
1. 로그인 (JWT / Access Token + Refresh Token)
2. 소셜 로그인 (Spring Security + OAuth)
3. 휴대폰 인증
4. 이메일 인증
5. 공고 등록 기능 (Session Sliding - Session Clustering + JWT)


![image](https://user-images.githubusercontent.com/83272619/156912407-d2e1019f-cab6-46e7-9d3a-8c8ce2f33deb.png)

## 아키텍처🚀🚀
1. DDD
2. Hexagonal Architecture
3. Multi Module Project
4. BFF
5. MSA


### DDD🚀
DDD에 대한 개념은 기술 서적을 읽고 전체 서비스에서 엔티티의 관점에서 바운디드 컨텍스트를 나누고 
Aggreagate을 적극적으로 활용할 생각입니다.

`DDD(도메인 주도 설계 핵심)`
`IDDD(도메인 주도 설계 구현)`
![image](https://user-images.githubusercontent.com/83272619/157800311-a3881e66-2a31-4a22-b05e-bf9d62f60ffa.png)


### Hexagonal Architecture🚀
Hexagonal Architecture의 경우 Port와 Adapter의 개념을 통해 역활을 명확히 구분할 생각입니다.
또한 Hexagonal Architecture의 핵심은 DIP라고 생각하기 때문에 프로젝트 전반적으로 DIP를 적용하여
유연한 확장이 가능한 로직 작성을 중요시 할 생각입니다.


### Multi Module Project🚀
Multi Module로 프로젝트를 구성하며 명확한 계층을 가질 생각입니다. 멀티 모듈의 핵심은 재사용과 확장성이라고 생각하지만, 
이러한 멀티 모듈의 장점을 극대화 하기 위해서는 명확한 계층 구조가 필요하다고 생각합니다.

![image](https://user-images.githubusercontent.com/83272619/157800254-bce90548-3974-4cb9-9e80-aca2a886873b.png)

계층별로 모듈을 구성하여 재사용뿐 아니라 확장에도 유연한 구조를 목표로 하고 있습니다.



### BFF🚀
BFF 구조를 통해 클라이언트는 도메인 구조를 알지 못해도 개발을 진행할 수 있도록 하는 것을 목표로 합니다.
또한 클라이언트 종류(웹, 안드로이드, IOS)에 따라 차별화된 데이터를 제공해 주며, BFF에서 데이터를 필터링 혹은 
마스킹 처리를 할 수 있도록 합니다.



### MSA🚀
현재 프로젝트는 기존 레거시 시스템을 MSA로 전환하는 것을 목표로 하고 있습니다. 이상적인 MSA를 위해서는
Spring Cloud를 이용한 게이트웨이 구현이 아닌 서비스 메쉬를 구현해야 하지만 어려운 러닝커브와  짧은 기간으로 이핸
MSA환경을 가정하고 진행할 생각입니다.

`마이크로서비스 도입 이렇게 한다` `스프링으로 하는 마이크로서비스 구축`

![image](https://user-images.githubusercontent.com/83272619/157800208-d5546975-c88e-4183-8c28-9df47750ab9d.png)




### Key Point🚀🚀
1. MapStruct을 이용하여 Entity와 DTO를 매핑하여 변화기 쉬운 DTO가 Repository 계층 접근을 차단한다.
2. Feign을 사용하여 api를 호출한다.
3. Global Session을 이용하여 Scale Out된 서버간에 세션을 동기화 한다.
4. Session, Access Token, Refresh Token을 사용하여 단발성이 아닌 서비스들은 지속적으로 이용할 수 있게 한다.
5. TDD를 익히자..!


### 구현 Issue🚀🚀
Q. OAuth의 경우 중복 회원가입을 어떻게 방지할 것인가? 
A. DI(Duplication Information)로 해결 할 수 있을까?

Q. 왜 Spring Security를 사용하면 인증, 인가를 Filter에 구현할까?
A. https://mangkyu.tistory.com/76 망나니 개발자 블로그 어딘가 있음..~!

Q. 게시판 작성중 세션이 만료되면 어떻게 작성했던 글을 캐싱할까?
A. 웹 캐시? < 프론트 영역
A. 캐싱? < 음.. 비연결성...
A. 중간중간 저장? < 이게 현실적인데..
A. 게시글 저장 시점에 세션,토큰 만료시 세션 재발급? < Expire 시간 재할당 가능?


### 운영 Issue🚀🚀



