FROM maven:3-eclipse-temurin-21-jammy

WORKDIR /application/

RUN chown 1001 /application \
    && chmod "g+rwX" /application \
    && chown 1001:root /application \
    && chmod 1777 /tmp

COPY --chown=1001:root target/quarkus-app/ ./

RUN mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install-deps"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./quarkus-run.jar"]