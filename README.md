# Simple Scheduler API

- 내일배움캠프 Spring 8기 트랙 Chapter 3 일정 관리 앱 과제에 대한 리포지토리입니다.

## 1. 프로젝트 개요

- 간단한 일정과 댓글을 관리할 수 있는 API 서버입니다.
- Spring Boot와 Spring Web MVC 그리고 Spring Data JPA를 이용하여 개발했습니다.
- 개발 일정
    - 최초: 2025년 7월 29일 오전 10:00 → 2025년 8월 4일 오후 2:00
- 개발 인원: 1인

## 2. 주요 기능 및 주안점

### 2.1 주요 기능

- **일정 관리 (Lv.1 ~ Lv.4)**
    - 일정 생성 (제목, 내용, 작성자, 비밀번호)
    - 전체 및 특정 작성자별 일정 목록 조회 (수정일 기준 내림차순 정렬)
    - 특정 일정 조회
    - 일정 정보 수정 (비밀번호 일치 시)
    - 일정 삭제 (비밀번호 일치 시)
- **댓글 관리 (Lv.5 ~ Lv.6)**
    - 선택한 일정에 댓글 등록 (최대 10개)
    - 특정 일정 조회 시, 해당 일정의 댓글 목록 함께 반환
- **유효성 검증 (Lv.7)**
    - 요청 데이터의 제약 조건(길이, 필수값 등) 검증
    - 비밀번호 불일치 등 예외 상황에 대한 처리

### 2.2 주안점

- OOP의 개념을 이해하고, OOP로 이루어진 Spring 프레임워크를 이해하면서 개발하기 위해 노력했습니다.
- 3 Layer Architecture(Controller, Service, Repository) 를 이해하고 적용하기 위해 노력했습니다.
- HTTP를 이해하고 올바른 RESTful API 및 클라이언트 개발자와의 협업을 고려하면서 API를 설계했습니다.
- 모던 Java의 문법을 의식적으로 사용하기 위해 노력했습니다.

## 2. 개발 환경

- **언어**: Java 17
- **프레임워크**: Spring Boot 3.2.x
- **데이터베이스**: MySQL 8.x
- **ORM**: Spring Data JPA
- **빌드 도구**: Gradle
- **기타**: Lombok, Postman

## 3. 프로젝트 구조 및 실행 방법

### 3.1 프로젝트 구조

```
Level1~7.io.github.seonrizee.scheduler
      ├── controller  # 1. 클라이언트의 요청을 받고 응답을 반환하는 계층
      ├── dto         # 2. 계층 간 데이터 전송을 위한 객체 (Request/Response)
            ├── request
            └── response
      ├── entity      # 3. 데이터베이스 테이블과 매핑되는 JPA 엔티티
      ├── exception   # 4. 전역 예외 처리를 위한 핸들러 및 커스텀 예외 클래스
      ├── mapper      # 5. Entity와 DTO 간의 변환 로직 담당
      ├── repository  # 6. DB에 접근하는 데이터 액세스 계층
      ├── service     # 7. 핵심 비즈니스 로직을 처리하는 계층
      └── validator   # 8. 요청 데이터의 유효성을 검증하는 로직 담당
            └── field

```

- Level 1~7까지 단계별로 모듈로 존재하며, 각 모듈마다 단계별로 과제 내용을 구현했습니다.

### 3.2 실행 방법

1. build.gradle 을 이용하여 의존성 동기화
2. .env.example 파일을 열어서 양식에 맞게 환경변수 내용 작성
3. 원하는 방식으로 환경변수 주입
    - 개발 환경에서는 간단히 Intellij 실행 환경 구성-옵션 수정-환경 변수 파일 등록 방법을 사용
4. 최종 버전인 **Level7** 모듈의 `Level7Application.java` 파일의 `main` 메소드를 실행
5. 애플리케이션이 `localhost:8080`에서 실행되면 Postman 등의 API 테스트 도구를 사용하여 테스트

## 4. 주요 설계 및 트러블 슈팅

### 4.1 ERD (Entity Relationship Diagram)

![ERD_v2.png](docs/image/ERD_v2.png)

- 과제 조건 중 연관관계를 사용하지 말고 구현을 고민해보라는 가이드가 있어 외래키를 사용한 연관관계 설정을 하지 않고 논리 관계만 이용했습니다.

### 4.2 RESTful API 설계

**4.2.1. API 요약**

| **기능**   | **HTTP Method** | **URL**                    | **설명**            |
|----------|-----------------|----------------------------|-------------------|
| **[일정]** |                 |                            |                   |
| 일정 생성    | `POST`          | `/schedules`               | 새로운 일정을 등록합니다.    |
| 전체 일정 조회 | `GET`           | `/schedules`               | 모든 일정을 조회합니다.     |
| 선택 일정 조회 | `GET`           | `/schedules/{id}`          | 특정 ID의 일정을 조회합니다. |
| 일정 수정    | `PATCH`         | `/schedules/{id}`          | 특정 ID의 일정을 수정합니다. |
| 일정 삭제    | `DELETE`        | `/schedules/{id}`          | 특정 ID의 일정을 삭제합니다. |
| **[댓글]** |                 |                            |                   |
| 댓글 생성    | `POST`          | `/schedules/{id}/comments` | 특정 일정에 댓글을 작성합니다. |

---

**4.2.2 API 상세 명세**

### 일정 생성

- **Method**: `POST`
- **URL**: `/schedules`
- **Description**: 새로운 일정을 등록합니다.

#### Request Body

```json
{
  "title": "String",
  "contents": "String",
  "username": "String",
  "password": "String"
}
```

#### Response (201 Created)

```json
{
  "statusCode": 201,
  "message": "요청을 성공적으로 처리했습니다.",
  "data": {
    "id": 1,
    "title": "새로운 일정",
    "contents": "새로운 일정 내용",
    "username": "작성자",
    "createdAt": "2025-08-01T10:00:00",
    "updatedAt": "2025-08-01T10:00:00",
    "comments": null
  }
}
```

-----

### 전체 일정 조회

- **Method**: `GET`
- **URL**: `/schedules`
- **Description**: 모든 일정을 조회합니다. `username`으로 필터링할 수 있습니다.

#### Query Parameters

- `username` (String, Optional): 조회할 작성자 이름

#### Response (200 OK)

```json
{
  "statusCode": 200,
  "message": "요청을 성공적으로 처리했습니다.",
  "data": [
    {
      "id": 2,
      "title": "두 번째 일정",
      "contents": "내용...",
      "username": "사용자B",
      "createdAt": "2025-08-01T11:00:00",
      "updatedAt": "2025-08-01T12:00:00",
      "comments": null
    },
    {
      "id": 1,
      "title": "첫 번째 일정",
      "contents": "내용...",
      "username": "사용자A",
      "createdAt": "2025-08-01T10:00:00",
      "updatedAt": "2025-08-01T10:00:00",
      "comments": null
    }
  ]
}
```

-----

### 선택 일정 조회

- **Method**: `GET`
- **URL**: `/schedules/{id}`
- **Description**: 특정 ID의 일정을 조회합니다. 댓글이 존재하는 경우 전체 댓글을 포함하여 응답합니다.

#### Path Parameters

- `id` (Number): 조회할 일정의 ID

#### Response (200 OK) - 댓글이 존재하지 않는 경우

```json
{
  "statusCode": 200,
  "message": "요청을 성공적으로 처리했습니다.",
  "data": {
    "id": 1,
    "title": "첫 번째 일정",
    "contents": "내용...",
    "username": "사용자A",
    "createdAt": "2025-08-01T10:00:00",
    "updatedAt": "2025-08-01T10:00:00",
    "comments": []
  }
}
```

#### Response (200 OK) - 댓글이 존재하는 경우

```json
{
  "statusCode": 200,
  "message": "요청을 성공적으로 처리했습니다.",
  "data": {
    "id": 1,
    "title": "첫 번째 일정",
    "contents": "내용...",
    "username": "사용자A",
    "createdAt": "2025-08-01T10:00:00",
    "updatedAt": "2025-08-01T10:00:00",
    "comments": [
      {
        "id": 101,
        "contents": "첫 번째 댓글입니다.",
        "username": "댓글러",
        "createdAt": "2025-08-01T10:05:00",
        "updatedAt": "2025-08-01T10:05:00"
      }
    ]
  }
}
```

-----

### 일정 수정

- **Method**: `PATCH`
- **URL**: `/schedules/{id}`
- **Description**: 특정 ID의 일정 정보를 수정합니다. (제목, 작성자명만 수정 가능)

#### Path Parameters

- `id` (Number): 수정할 일정의 ID

#### Request Body

```json
{
  "title": "String",
  "username": "String",
  "password": "String"
}
```

#### Response (200 OK)

```json
{
  "statusCode": 200,
  "message": "요청을 성공적으로 처리했습니다.",
  "data": {
    "id": 1,
    "title": "수정된 제목",
    "contents": "내용... (수정 안 됨)",
    "username": "수정된 작성자명",
    "createdAt": "2025-08-01T10:00:00",
    "updatedAt": "2025-08-01T13:00:00",
    "comments": null
  }
}
```

-----

### 일정 삭제

- **Method**: `DELETE`
- **URL**: `/schedules/{id}`
- **Description**: 특정 ID의 일정을 삭제합니다.

#### Path Parameters

- `id` (Number): 삭제할 일정의 ID

#### Request Body

```json
{
  "password": "String"
}
```

#### Response (204 No Content) -> (200 OK)

- 본래 성공 시, 응답 본문(Body) 없음이었으나, 클라이언트에서 일관성 있는 처리를 할 수 있도록 200 OK로 수정

```json
{
  "statusCode": 200,
  "message": "요청을 성공적으로 처리했습니다.",
  "data": null
}
```

-----

### 댓글 생성

- **Method**: `POST`
- **URL**: `/schedules/{id}/comments`
- **Description**: 특정 일정에 새로운 댓글을 작성합니다.

#### Path Parameters

- `id` (Number): 댓글을 작성할 일정의 ID

#### Request Body

```json
{
  "contents": "String",
  "username": "String",
  "password": "String"
}
```

#### Response (201 Created)

```json
{
  "statusCode": 201,
  "message": "요청을 성공적으로 처리했습니다.",
  "data": {
    "id": 102,
    "contents": "새로운 댓글 내용",
    "username": "새로운 댓글 작성자",
    "createdAt": "2025-08-01T14:00:00",
    "updatedAt": "2025-08-01T14:00:00"
  }
}
```

### 4.3 설계 및 트러블 슈팅 회고

## 5. 브랜치 전략 및 커밋 컨벤션

### **5.1 브랜치 전략**

- develop 브랜치 개발 → main 브랜치 PR 적용
- 별도의 git flow나 github flow같은 브랜치 전략은 1인 프로젝트 과제 편의상 사용하지 않음

### **5.2 커밋 컨벤션**

```
feat: 새로운 기능 추가
fix: 버그 수정
refactor: 코드 리펙토링
test: 테스트 코드 추가 및 수정
docs: 문서 추가 및 수정
chore: code와 관련 없는 설정, 변경
```

- `타입(레벨): 제목` 형식으로 작성
- 일부 메시지는 상세 설명 작성
- [**Conventional Commits 참고**](https://www.conventionalcommits.org/ko/v1.0.0/)

## 6. 향후 개선 과제

1. **테스트 코드 작성**: JUnit5, Mockito 등을 활용하여 Service, Controller 계층에 대한 단위 테스트 추가 수정 및 통합 테스트 코드를 작성
2. **Swagger/OpenAPI 적용**: API 명세서를 코드를 통해 자동으로 생성하고, 테스트 가능한 UI를 제공하여 API 문서 관리의 효율성 증대.
3. **고아 객체 제거**: 현재 일정 삭제는 일정만 삭제되고 있으므로, 일정 삭제 시 관련된 댓글까지 삭제되도록 확장 방법에 대해 고민해보기.
4. **일정 전체 조회 시 댓글 함께 조회**: 현재는 일정 전체 조회에서는 댓글을 가져오지 않으므로, 댓글도 함께 가져오도록 기능을 구현하며 JPA N+1 문제에 대해 고민해보기.