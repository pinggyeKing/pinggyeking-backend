FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .
RUN ./gradlew clean build --no-daemon
COPY build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]