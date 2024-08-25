FROM maven:3-eclipse-temurin-21-alpine

WORKDIR /application/

RUN chown 1001 /application \
    && chmod "g+rwX" /application \
    && chown 1001:root /application
COPY --chown=1001:root target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["./app.jar", "-Dquarkus.http.host=0.0.0.0"]