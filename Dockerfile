FROM eclipse-temurin:17-jre
WORKDIR /app
COPY app.jar /app/app.jar
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8 JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
