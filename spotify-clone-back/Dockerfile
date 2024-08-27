FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn -f pom.xml clean package -Dmaven.test.skip

FROM openjdk:17-alpine
COPY --from=build /target/spotify-clone-back-0.0.1-SNAPSHOT.jar spotify-clone.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","spotify-clone.jar"]