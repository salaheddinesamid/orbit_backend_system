
FROM eclipse-temurin:17-jre

LABEL authors="salaheddine"

WORKDIR /myapp

COPY target/orbit-banking-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["sh", "-c", "exec java -jar app.jar --spring.profiles.active=${ENV_PROFILE:-test}"]
