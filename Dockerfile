FROM openjdk:17-jdk-alpine
EXPOSE 8080
WORKDIR /usr/src/java-app
COPY build/libs/template-kotlin.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]