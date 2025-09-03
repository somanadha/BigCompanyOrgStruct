# Stage 1: Build the application and resolve dependencies
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY data.csv /app/data.csv
COPY src ./src

# Download dependencies only to cache them for later build stages
# Download dependencies to the target/dependency directory for later build stages
RUN mvn dependency:copy-dependencies

# Package the application as a fat jar or a standard jar
RUN mvn -X package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-slim
WORKDIR /app

# Copy the application JAR from the build stage
COPY --from=build /app/target/*.jar bigcompany.jar

# Copy the datafile
COPY --from=build /app/data.csv ./data.csv

# This is the key part to get all the dependency jars
# The 'go-offline' goal from the first stage already downloaded them.
# The 'dependency:copy-dependencies' goal copies all the jars to a specific directory.
COPY --from=build /app/target/dependency /app/dependency

# Command to run the application with all dependencies on the classpath
CMD ["java", "-cp", "bigcompany.jar:dependency/*", "com.big.company.BigCompany"]
#ENTRYPOINT ["java", "-jar", "bigcompany.jar"]
