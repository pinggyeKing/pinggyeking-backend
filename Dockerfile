# 빌드 스테이지
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Gradle 래퍼 복사
COPY gradlew .
COPY gradle gradle

# Gradle Kotlin DSL 설정 파일들 복사
COPY build.gradle.kts .
COPY settings.gradle.kts .

# 소스 코드 복사
COPY src src

# 실행 권한 부여 후 빌드
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# 실행 스테이지
FROM openjdk:17-jdk-slim

WORKDIR /app

# 빌드 스테이지에서 jar 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]