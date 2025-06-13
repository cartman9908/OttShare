# 1단계: Gradle 빌드 단계 (ARM64 지원 이미지)
FROM gradle:8.12.1-jdk17 AS build

# 캐시 활용을 위해 Gradle 관련 파일 먼저 복사
WORKDIR /app
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# 의존성 캐싱
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# 나머지 소스 복사
COPY . /app

# JAR 빌드
RUN ./gradlew clean bootJar --no-daemon

# 2단계: 경량 실행 환경
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 컨테이너 외부 노출 포트
EXPOSE 8081

# 실행 명령
ENTRYPOINT ["java", "-jar", "/app/app.jar"]