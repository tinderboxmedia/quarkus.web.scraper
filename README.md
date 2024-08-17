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
curl -H "x-api-key:key123" -v http://localhost:8080/scrape
```

Example CURL to call the POST endpoint:

```shell script
curl -X POST -H "x-api-key:key123" -H "Content-Type: application/json" -d "{}" -v http://localhost:8080/scrape
```

## Creating a native executable

You can let the Jenkins pipeline manage that. Just push your changes to the GitHub repo.
