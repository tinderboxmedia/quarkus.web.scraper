# quarkus-web-scraper

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

## Running the unit tests

Soon...

## Running the integration tests

Soon...

## Example Calls

Example CURL to call the GET endpoint:

```shell script
curl -v -H "x-api-key:key123" http://localhost:8080/scrape
```

Example CURL to call the POST endpoint:

```shell script
curl -v -H "x-api-key:key123" -H "Content-Type: application/json" -d "{\"url\":\"https://playwright.dev/java/\", \"selector\":\"text='Playwright for Java'\", \"extraWait\":500}" http://localhost:8080/scrape
```
