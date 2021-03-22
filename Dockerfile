FROM openjdk:8
#FROM maven:3.6.2-jdk-8
WORKDIR /src
COPY ./target/java_simple_app-1.0-SNAPSHOT.jar ./src/
ENTRYPOINT ["java", "-jar", "src/java_simple_app-1.0-SNAPSHOT.jar"]
#CMD java -classpath