FROM gradle:7.3.0-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle/

RUN ./gradlew dependencies

COPY src ./src/

RUN ./gradlew bootJar -x test

FROM openjdk:17-oracle

ARG JAR_FILE=/app/build/libs/*.jar

COPY --from=build ${JAR_FILE} app.jar

EXPOSE $API_PORT

ENV API_PORT=${API_PORT}
ENV ETH_NODE_URL=${ETH_NODE_URL}
ENV DB_CONNECTION_URL=${DB_CONNECTION_URL}

ENTRYPOINT ["java", "-jar", "/app.jar"]