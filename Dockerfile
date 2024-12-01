FROM eclipse-temurin:21.0.5_11-jre

COPY /build/libs/*.jar app.jar
EXPOSE 8080 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

