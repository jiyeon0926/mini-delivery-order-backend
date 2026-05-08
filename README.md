# 🍴 EAT NOW | Java & Spring Boot 기반 REST API 서버
EAT NOW 서버는 배달 주문 서비스를 위한 Backend 시스템으로, REST API 기반으로 설계되었습니다. <br>
고객과 사장님에 따라 제공되는 기능이 구분되며, 가게 검색부터 장바구니 및 주문 기능까지 기본 기능을 제공합니다. <br>
결제 기능은 포함하지 않은 미니 배달 주문 서비스입니다. <br><br>
![Project](https://img.shields.io/badge/Project-EAT%20NOW(Backend)-orange)
![Service](https://img.shields.io/badge/Service-배달%20주문%20REST%20API-blue)
![Team](https://img.shields.io/badge/Team-2인%20프로젝트-purple)
![Period](https://img.shields.io/badge/Period-2026.04.06~2026.05.08-green)

# 📍 목차
[![주요 기능](https://img.shields.io/badge/주요%20기능-FF6B6B?style=for-the-badge)](#주요-기능)
[![기술 스택](https://img.shields.io/badge/기술%20스택-4DABF7?style=for-the-badge)](#기술-스택)
[![요구사항](https://img.shields.io/badge/요구사항-20C997?style=for-the-badge)](#요구사항)
[![설계](https://img.shields.io/badge/설계-845EF7?style=for-the-badge)](#설계)
[![역할 분담](https://img.shields.io/badge/역할%20분담-FF922B?style=for-the-badge)](#역할-분담)
[![프로젝트 구조](https://img.shields.io/badge/프로젝트%20구조-ADB5BD?style=for-the-badge)](#프로젝트-구조)

## 주요 기능
- 가게 관리
  - 가게 생성/수정/폐업/조회
  - 가게 영업 상태 변경
  - Keyword 기반 가게 검색 (가게명, 메뉴명 기준)

- 메뉴 관리
  - 메뉴 생성/수정/삭제

- 장바구니
  - 메뉴 추가
  - 항목 수량 변경
  - 항목 삭제
  - 장바구니 조회

- 주문
  - 주문 생성
  - 주문 내역 조회
  - 주문 취소/거절
  - 주문 상태 변경
 
- 리뷰
  - 리뷰 작성/삭제/조회

## 기술 스택
![Java](https://img.shields.io/badge/Java%2021-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%204.0.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=jsonwebtokens)
![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-0769AD?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)

## 요구사항
- [요구사항 명세서](https://www.notion.so/3391445a106f80c7b6f7d836599e5dbc?source=copy_link)

## 설계
- [와이어프레임](https://www.figma.com/design/xZCTKUYTooFRuDhzNVi86N/Eat-Now?node-id=0-1&t=zVkmJCPw1Cp3AkvH-1)
- [API 명세서](https://www.notion.so/33b1445a106f80b9aa2de873dcb7cedd?v=33b1445a106f809c9a3c000c4e046444&source=copy_link)
- [ERD](https://www.notion.so/ERD-33b1445a106f8075ae0acf0df7cca185?source=copy_link)
<img width="2248" height="800" alt="delivery-order" src="https://github.com/user-attachments/assets/402c5426-404a-497e-bdc8-f546e2ba33aa" />

## 역할 분담
| 이름 | 담당 기능 | GitHub | Blog |
| --- | --- | --- | --- |
| 김지연 | - 인증 / 인가 <br> - 사용자 CRUD <br> - 가게 CR <br> - 장바구니 CRUD <br> - 주문 CRUD | [GitHub](https://github.com/jiyeon0926) | - [학습 기록](https://blog.naver.com/yeondata) <br> - [문제 해결 기록](https://velog.io/@yeoni9094/posts) |
| 임현아 | - 가게 RUD <br> - 메뉴 CUD <br> - 리뷰 CRD | [GitHub](https://github.com/gaebarja99) | [개발 기록](https://blog.naver.com/bbaehyunn) |

### 김지연
```
- 고객 회원가입 / 사장님 회원가입
- 로그인 / 로그아웃
- 닉네임 변경 / 비밀번호 변경 / 회원탈퇴
- 프로필 조회
- 가게 생성 / 사용자 가게 검색 / 가게 단건 조회
- 장바구니 아이템 추가 / 조회 / 삭제 / 아이템 수량 변경
- 주문 / 고객 주문 내역 조회 / 고객 주문 단건 조회
- 전체 주문 수 조회 / 가게 주문 목록 조회 / 가게 주문 수 조회 / 가게 주문 단건 조회
- 주문 취소 / 주문 상태 변경 / 주문 거절
```

### 임현아
```
- 가게 수정 / 가게 영업 상태 변경
- 본인 가게 다건 조회 / 가게 폐업
- 메뉴 생성 / 수정 / 삭제
- 리뷰 작성 / 가게 리뷰 조회 / 리뷰 삭제 / 내 리뷰만 조회
```

## 프로젝트 구조
```
\---src
    +---main
    |   +---java
    |   |   \---mini
    |   |       \---delivery
    |   |           |   DeliveryApplication.java
    |   |           |
    |   |           +---domain
    |   |           |   +---auth
    |   |           |   |   +---controller
    |   |           |   |   |       AuthController.java
    |   |           |   |   |
    |   |           |   |   +---dto
    |   |           |   |   |       AuthTokenResponseDto.java
    |   |           |   |   |       LoginRequestDto.java
    |   |           |   |   |       SignupRequestDto.java
    |   |           |   |   |       SignupResponseDto.java
    |   |           |   |   |
    |   |           |   |   \---service
    |   |           |   |           AuthService.java
    |   |           |   |
    |   |           |   +---cart
    |   |           |   |   +---controller
    |   |           |   |   |       CartController.java
    |   |           |   |   |
    |   |           |   |   +---dto
    |   |           |   |   |       CartItemCreateRequestDto.java
    |   |           |   |   |       CartItemResponseDto.java
    |   |           |   |   |       CartResponseDto.java
    |   |           |   |   |       QuantityUpdateRequestDto.java
    |   |           |   |   |
    |   |           |   |   +---entity
    |   |           |   |   |       Cart.java
    |   |           |   |   |       CartItem.java
    |   |           |   |   |
    |   |           |   |   +---repository
    |   |           |   |   |       CartItemRepository.java
    |   |           |   |   |       CartRepository.java
    |   |           |   |   |
    |   |           |   |   \---service
    |   |           |   |           CartService.java
    |   |           |   |
    |   |           |   +---menu
    |   |           |   |   +---controller
    |   |           |   |   |       MenuController.java
    |   |           |   |   |
    |   |           |   |   +---dto
    |   |           |   |   |       MenuCreateRequestDto.java
    |   |           |   |   |       MenuCreateResponseDto.java
    |   |           |   |   |       MenuUpdateRequestDto.java
    |   |           |   |   |
    |   |           |   |   +---entity
    |   |           |   |   |       Menu.java
    |   |           |   |   |
    |   |           |   |   +---repository
    |   |           |   |   |       MenuRepository.java
    |   |           |   |   |
    |   |           |   |   \---service
    |   |           |   |           MenuService.java
    |   |           |   |
    |   |           |   +---order
    |   |           |   |   +---controller
    |   |           |   |   |       CustomerOrderController.java
    |   |           |   |   |       OwnerOrderController.java
    |   |           |   |   |
    |   |           |   |   +---dto
    |   |           |   |   |       CustomerOrderDetailResponseDto.java
    |   |           |   |   |       CustomerOrderItemResponseDto.java
    |   |           |   |   |       CustomerOrderResponseDto.java
    |   |           |   |   |       DailyOrderCountResponseDto.java
    |   |           |   |   |       OrderCreateRequestDto.java
    |   |           |   |   |       OrderCreateResponseDto.java
    |   |           |   |   |       OrderItemDetailResponseDto.java
    |   |           |   |   |       OrderRejectRequestDto.java
    |   |           |   |   |       OrderRejectResponseDto.java
    |   |           |   |   |       OrderStatusCountResponseDto.java
    |   |           |   |   |       OrderStatusUpdateRequestDto.java
    |   |           |   |   |       OrderStatusUpdateResponseDto.java
    |   |           |   |   |       OwnerOrderDetailResponseDto.java
    |   |           |   |   |       OwnerOrderSummaryResponseDto.java
    |   |           |   |   |       OwnerStoreOrderResponseDto.java
    |   |           |   |   |
    |   |           |   |   +---entity
    |   |           |   |   |       Order.java
    |   |           |   |   |       OrderItem.java
    |   |           |   |   |
    |   |           |   |   +---repository
    |   |           |   |   |       OrderItemRepository.java
    |   |           |   |   |       OrderRepository.java
    |   |           |   |   |       OrderStatusAndCountOnly.java
    |   |           |   |   |
    |   |           |   |   \---service
    |   |           |   |           CustomerOrderService.java
    |   |           |   |           OwnerOrderService.java
    |   |           |   |
    |   |           |   +---review
    |   |           |   |   +---controller
    |   |           |   |   |       ReviewController.java
    |   |           |   |   |
    |   |           |   |   +---dto
    |   |           |   |   |       MyReviewResponseDto.java
    |   |           |   |   |       ReviewCreateRequestDto.java
    |   |           |   |   |       ReviewCreateResponseDto.java
    |   |           |   |   |       ReviewListResponseDto.java
    |   |           |   |   |       StoreReviewResponseDto.java
    |   |           |   |   |
    |   |           |   |   +---entity
    |   |           |   |   |       Review.java
    |   |           |   |   |
    |   |           |   |   +---repository
    |   |           |   |   |       ReviewRepository.java
    |   |           |   |   |
    |   |           |   |   \---service
    |   |           |   |           ReviewService.java
    |   |           |   |
    |   |           |   +---store
    |   |           |   |   +---controller
    |   |           |   |   |       StoreController.java
    |   |           |   |   |
    |   |           |   |   +---dto
    |   |           |   |   |       OwnerStoreResponseDto.java
    |   |           |   |   |       StoreCreateRequestDto.java
    |   |           |   |   |       StoreCreateResponseDto.java
    |   |           |   |   |       StoreMenuResponseDto.java
    |   |           |   |   |       StoreResponseDto.java
    |   |           |   |   |       StoreStatusChangeDto.java
    |   |           |   |   |       StoreStatusChangeResponseDto.java
    |   |           |   |   |       StoreSummaryDto.java
    |   |           |   |   |       StoreSummaryResponseDto.java
    |   |           |   |   |       StoreUpdateRequestDto.java
    |   |           |   |   |
    |   |           |   |   +---entity
    |   |           |   |   |       Store.java
    |   |           |   |   |
    |   |           |   |   +---repository
    |   |           |   |   |       CustomStoreRepository.java
    |   |           |   |   |       CustomStoreRepositoryImpl.java
    |   |           |   |   |       StoreRepository.java
    |   |           |   |   |
    |   |           |   |   \---service
    |   |           |   |           StoreService.java
    |   |           |   |
    |   |           |   \---user
    |   |           |       +---controller
    |   |           |       |       UserController.java
    |   |           |       |
    |   |           |       +---dto
    |   |           |       |       NicknameRequestDto.java
    |   |           |       |       NicknameResponseDto.java
    |   |           |       |       PasswordRequestDto.java
    |   |           |       |       UserResponseDto.java
    |   |           |       |
    |   |           |       +---entity
    |   |           |       |       User.java
    |   |           |       |
    |   |           |       +---repository
    |   |           |       |       UserRepository.java
    |   |           |       |
    |   |           |       \---service
    |   |           |               UserService.java
    |   |           |
    |   |           \---global
    |   |               +---auth
    |   |               |   |   AuthenticationScheme.java
    |   |               |   |   RefreshTokenService.java
    |   |               |   |   UserDetailsImpl.java
    |   |               |   |   UserDetailsServiceImpl.java
    |   |               |   |
    |   |               |   +---handler
    |   |               |   |       DelegatedAccessDeniedHandler.java
    |   |               |   |       DelegatedAuthenticationEntryPoint.java
    |   |               |   |
    |   |               |   \---jwt
    |   |               |           JwtAuthFilter.java
    |   |               |           JwtProvider.java
    |   |               |
    |   |               +---common
    |   |               |   +---dto
    |   |               |   |       CommonResponseBody.java
    |   |               |   |
    |   |               |   +---entity
    |   |               |   |       BaseEntity.java
    |   |               |   |
    |   |               |   \---enums
    |   |               |           OrderStatus.java
    |   |               |           Role.java
    |   |               |           StoreStatus.java
    |   |               |
    |   |               +---config
    |   |               |       CorsConfig.java
    |   |               |       QuerydslConfig.java
    |   |               |       RedisConfig.java
    |   |               |       SecurityConfig.java
    |   |               |
    |   |               \---error
    |   |                       CustomException.java
    |   |                       ErrorCode.java
    |   |                       ErrorResponse.java
    |   |                       GlobalExceptionHandler.java
    |   |
    |   \---resources
    |       |   application.properties
    |       |   schema.sql
    |       |
    |       +---static
    |       \---templates
    \---test
        \---java
            \---mini
                \---delivery
                        DeliveryApplicationTests.java
```
