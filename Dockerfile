FROM openjdk:21-oracle
VOLUME /tmp
#COPY target/todo-list-0.0.1-SNAPSHOT.jar tasks.jar
#OR
COPY target/*.jar tasks.jar
ENTRYPOINT ["java","-jar", "tasks.jar"]

