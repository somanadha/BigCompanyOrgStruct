FROM openjdk:24-slim-bullseye
ADD target/bigcompany.jar bigcompany.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "bigcompany.jar"]