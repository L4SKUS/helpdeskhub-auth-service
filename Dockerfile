FROM openjdk:17-jdk-slim
COPY target/auth-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]