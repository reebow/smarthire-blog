FROM eclipse-temurin:21-jre-jammy
RUN groupadd --system spring && useradd --system --gid spring spring
USER spring:spring
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080