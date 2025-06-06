FROM gradle:8.14-jdk17-corretto AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle build -x test

FROM amazoncorretto:17-alpine3.17-jdk
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]