FROM maven:3-eclipse-temurin-21-alpine

WORKDIR /application/

RUN chown 1001 /application \
    && chmod "g+rwX" /application \
    && chown 1001:root /application
COPY --chown=1001:root target/quarkus-app/ /quarkus-app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./quarkus-app/quarkus-run.jar.jar"]