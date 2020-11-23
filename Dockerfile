FROM openjdk:11.0.9.1-slim
MAINTAINER  Ruben Quispe <ruben.quispem@gmail.com>
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /usr/app/app.jar
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "app.jar"]