FROM openjdk:21-oracle
VOLUME /tmp
COPY target/todo-list-0.0.1-SNAPSHOT.jar tasks.jar
#OR

#COPY target/*.jar tasks.jar
ENTRYPOINT ["java","-jar", "tasks.jar"]

## In terminal, if we repect the filename as Dockerfile, we do'nt need to right for ex : ($ docker build . -t Dockerfile:v1) to create image.
## with file name Dockerfile we can especify an ather name like ($ docker build . -t do-tasks:v1)