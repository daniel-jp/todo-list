FROM eclipse-temurin:21-jdk
RUN apt-get update && apt-get install -y curl
VOLUME /tmp
WORKDIR /app
COPY target/*.jar app.jar

EXPOSE 8080
EXPOSE 8081
ENTRYPOINT ["java","-jar", "app.jar"]

## In terminal, if we repect the filename as Dockerfile, we do'nt need to right for ex : ($ docker build . -t Dockerfile:v1) to create image.
## with file name Dockerfile weclear can especify an ather name like ($ docker build . -t do-tasks:v1)