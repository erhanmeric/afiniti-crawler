FROM java:8-jdk-alpine
COPY ./target/afiniti-crawler-jar-with-dependencies.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "afiniti-crawler-jar-with-dependencies.jar"]
