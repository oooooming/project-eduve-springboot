# Eduve: RAG 기반 AI 챗봇 서비스

Eduve는 음성 인식(STT), OCR 문자 추출, 채팅 메시지 저장 등 기능을 제공하는 Spring Boot 기반의 교육 지원 백엔드 서버입니다. 이 프로젝트는 JWT 인증과 RESTful API를 기반으로 하며, 학생과 교사 간 커뮤니케이션을 지원합니다.

<br>
<br>

## 📦 프로젝트 구성 레포

본 프로젝트는 다음 세 개의 레포지토리로 구성되어 있습니다:

| 이름         | 설명                           | GitHub 주소 |
|--------------|--------------------------------|--------------|
| **springboot** | 백엔드 주요 로직 (API, DB, STT, JWT 등)  | [Eduve Spring Boot](https://github.com/TriCode-Ewha/eduve-backend-springboot) |
| **flask**     | AI 모델 기반 RAG  서버 (LangChain, 임베딩, 유사도 검색)     | [Eduve Flask AI](https://github.com/TriCode-Ewha/eduve-backend-flask) |
| **front**     | 프론트엔드 클라이언트 (웹 인터페이스, React) | [Eduve Front](https://github.com/TriCode-Ewha/eduve-frontend) |


<br>
<br>


## 1. Spring Boot (eduve-springboot)

RESTful API, 사용자 인증, DB 관리, STT, OCR 등 핵심 백엔드 기능을 담당합니다.

<br>

### 📁 주요 디렉토리 구조

```
eduve/
├── src/
│ └── main/
│ ├── java/tricode/eduve/
│ │ ├── config/                   # 설정 클래스 (Security 등)
│ │ ├── controller/               # REST API 엔드포인트
│ │ ├── service/                  # 비즈니스 로직
│ │ ├── dto/                      # 요청/응답 DTO
│ │ ├── domain/                   # Entity 클래스
│ │ ├── repository/               # JPA 레포지토리
│ │ ├── jwt/                      # 인증 필터, JWT 유틸
│ │ └── EduveApplication.java     # Spring Boot 메인 클래스
│ │
│ └── resources/
│ ├── application.yml             # 환경설정
│ 
├── scripts/
│ ├── start.sh
│ └── stop.sh
│
├── appspec.yml                   # AWS 배포 스크립트
├── build.gradle
├── settings.gradle
└── README.md
```

<br>


## 🧾 Source Code 설명

| 디렉토리 | 설명 |
|----------|------|
| `controller/` | REST API 요청 처리 |
| `service/` | 비즈니스 로직 구현 |
| `dto/` | 클라이언트 ↔ 서버 데이터 전달 구조 |
| `jwt/` | 로그인 필터, 토큰 발급 및 검증 |
| `repository/` | 데이터베이스 연동 (Spring Data JPA) |
| `resources/` | 환경설정 및 샘플 데이터 |

<br>

## 🛠 How to Build

Gradle을 사용하여 프로젝트를 빌드합니다.

```bash
./gradlew clean build
```

<br>



## 📦 How to Install & Run

빌드 후 다음 명령으로 실행할 수 있습니다:

```bash
java -jar build/libs/eduve-0.0.1-SNAPSHOT.jar
```
또는 스크립트를 통해 실행:

```bash
sh scripts/start.sh
```

<br>
<br>
<br>

---




## ✅ How to Test
이 프로젝트는 JUnit 5 및 Spring Boot Test를 기반으로 테스트를 수행합니다.

**테스트 실행**
```bash
./gradlew test
```
또는 IDE(IntelliJ 등)에서 src/test/ 디렉토리 내 테스트 클래스 수동 실행

***테스트 구조***
| 디렉토리 경로                        | 설명                 |
|-------------------------------------|----------------------|
| `src/test/java/.../service/`        | 서비스 단위 테스트   |
| `src/test/java/.../controller/`     | REST API 통합 테스트 |


<br>
<br>

## 📊 샘플 데이터 설명
프로젝트에는 API 테스트용 샘플 데이터가 포함되어 있습니다.

#### 1. 사용자 데이터 (users.csv)
- 위치: src/main/resources/sample/users.csv
- 형식: CSV

```csv
id,username,password,role
1,teacher01,password123,ROLE_TEACHER
2,student01,password456,ROLE_STUDENT
```

#### 2. 메시지 데이터 (messages.json)
- 위치: src/main/resources/sample/messages.json
- 형식: JSON

```json
[
  {
    "sender": "student01",
    "receiver": "teacher01",
    "message": "안녕하세요 선생님!",
    "timestamp": "2024-06-01T10:00:00"
  },
  {
    "sender": "teacher01",
    "receiver": "student01",
    "message": "네, 어떤 도움이 필요하신가요?",
    "timestamp": "2024-06-01T10:01:00"
  }
]
```

#### 3. 사용 예시 (샘플 업로드)
```bash

curl -X POST http://localhost:8080/api/users/import \
     -F 'file=@src/main/resources/sample/users.csv'
```

```bash

curl -X POST http://localhost:8080/api/messages/import \
     -H "Content-Type: application/json" \
     -d @src/main/resources/sample/messages.json
```

<br>
<br>


## 🗄 Database 사용 정보
- DBMS: MySQL (또는 MariaDB)
- 설정 예시 (application.yml):

``` yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/eduve
    username: eduve_user
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

<br>
<br>

## 📚 사용된 오픈소스 목록
| 라이브러리        | 설명                   | 라이선스     | 링크 |
|------------------|------------------------|--------------|------|
| Spring Boot      | 백엔드 프레임워크       | Apache 2.0   | [링크](https://spring.io/projects/spring-boot) |
| Spring Security  | 인증/인가 처리         | Apache 2.0   | [링크](https://spring.io/projects/spring-security) |
| jjwt             | JWT 토큰 처리           | Apache 2.0   | [링크](https://github.com/jwtk/jjwt) |
| Lombok           | 보일러플레이트 코드 제거 | MIT          | [링크](https://projectlombok.org) |
| Gradle           | 빌드 도구               | Apache 2.0   | [링크](https://gradle.org) |
| AWS CodeDeploy   | 배포 자동화             | -            | [링크](https://docs.aws.amazon.com/codedeploy) |




