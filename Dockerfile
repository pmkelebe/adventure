FROM openjdk:8-slim
COPY ./target/webscraper-1.0-SNAPSHOT.jar /webscraper.jar
WORKDIR /
ENTRYPOINT ["java", "-jar","webscraper.jar"]