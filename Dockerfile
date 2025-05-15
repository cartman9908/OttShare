# 1단계: Gradle 빌드 단계 (ARM64 지원)
FROM gradle:8.12.1-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 캐시 최적화
COPY . .

RUN gradle clean bootJar

FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 컨테이너 내에서 사용할 포트
EXPOSE 8080

# 애플리케이션 실행
CMD ["java", "-jar", "/app/app.jar"]