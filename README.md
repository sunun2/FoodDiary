# 🍽️ 한입만 (HannipmanApp)

> 나만의 식당 다이어리 앱 ✨\
> 지도로 식당을 찾고, 맛집에서의 추억을 일기로 남겨보세요!

---

## 📌 프로젝트 소개

한입만(Hannipman)은 사용자가 방문한 식당을 지도에서 확인하고, 그곳에서의 추억을 사진과 함께 일기로 남길 수 있는 **식당 다이어리 앱**입니다.\
카카오 로그인을 통해 사용자 인증을 간편하게 처리하고, 내가 남긴 일기들을 마이페이지에서 한눈에 확인할 수 있습니다.

---

## 💻 주요 기능

### 🗺️ 지도

- 현재 위치 기반 지도 표시 (Google Maps API)
- 식당 마커 클릭 시 관련 일기 목록 조회 (Bottom Sheet 형태)

### 📔 식탁일기

- 일기 생성, 수정, 삭제, 상세 조회, 리스트 조회
- 전체 일기 삭제 가능
- 사진 최대 5장 업로드
- 좋아요 기능
- 식당 정보와 연동된 일기 저장
- 총 일기 수와 좋아요(하트) 수 조회
- 
### 🙋 마이페이지
- 카카오 로그인 연동
- 카카오 프로필 정보 불러오기
- 정보 수정 후 저장 기능
---

## 🛠️ 내가 맡은 역할

| 기능         | 상세 설명                    |
| ---------- | ------------------------ |
| 🌐 지도 연동   | 지도에서 식당 클릭 시 관련 일기 보여주는 API 구현 |
| 📝 일기 기능   | 생성, 수정, 삭제, 상세조회, 리스트조회 기능 구현 |
| 📷 이미지 업로드 | 일기 작성 시 이미지 업로드 기능 구현    |
| 🔗 연동 기능   | 일기와 식당 정보 연동 설계 및 구현     |
| 👤 사용자 기능  | 카카오 로그인 및 마이페이지 기능 구현    |

---

## 🧑‍💻 팀 구성

| 이름      | 역할                       |
| ------- | ------------------------ |
| sunun2  | 백엔드 개발 (식당 DB, 일기 API 등) |
| peh1212 | 백엔드 개발 (식당 API, 지도 연동 등) |

---

## 📦 기술 스택

- Java 17 / Spring Boot
- MySQL / JPA / Hibernate
- Kakao Login API
- IntelliJ IDEA / GitHub
- Postman
- AWS (향후 S3 연동 예정)

---

## 📁 프로젝트 구조 예시

```bash
📦 HannipmanApp
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┗ 📂 com.hannipman
 ┃ ┃ ┃   ┣ 📂 controller
 ┃ ┃ ┃   ┣ 📂 domain
 ┃ ┃ ┃   ┣ 📂 dto
 ┃ ┃ ┃   ┣ 📂 repository
 ┃ ┃ ┃   ┗ 📂 service
 ┃ ┃ ┗ 📂 resources
 ┃ ┃   ┣ 📜 application.yml
 ┃ ┃   ┗ 📜 static / templates
 ┣ 📂 test
 ┣ 📜 build.gradle
 ┣ 📜 README.md
 ┗ 📜 .gitignore
```

---

## 🚀 실행 방법

### 💾 실행 전 준비사항

1. MySQL 실행 및 DB 생성 (`restaurant_db`)
2. `application.yml` 또는 `application.properties`에 다음 정보 추가:

```yml
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=9606
```

### ▶️ 실행

```bash
./gradlew build
./gradlew bootRun
```

---

## 🖼️ 스크린샷 (옵션)

> 추후 실제 앱 화면 캡처 추가 가능

---

## 🎉 개발 회고

- ✅ 지도 기반 마커 클릭 → 연관 일기 조회 흐름 구현 완료
- ✅ 다중 이미지 업로드 & 저장 기능 설계 및 테스트 완료
- ✅ 프론트엔드와 REST API 연동하며 협업 경험 향상
- ⚙️ S3 연동, 댓글 기능 등 추가 확장 가능성 검토 중

---

## ✨ 향후 발전 방향

- 이미지 저장: AWS S3 연동
- 사용자별 통계 및 일기 분석 기능
- 북마크한 식당 목록 보기 등


