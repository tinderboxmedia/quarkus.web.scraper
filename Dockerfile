FROM maven:3-eclipse-temurin-21-alpine

WORKDIR /app/

RUN chown 1001 /app \
    && chmod "g+rwX" /app \
    && chown 1001:root /app
COPY --chown=1001:root target/*.jar app.jar

EXPOSE 8080
USER 1001

ENTRYPOINT ["java","-jar","./app.jar"]