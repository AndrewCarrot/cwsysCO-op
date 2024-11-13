# Use OpenJDK as the base image
FROM openjdk:17-jdk-alpine

# Install Maven
RUN apk add --no-cache maven

# Set environment variables for PostgreSQL
ENV POSTGRES_PASSWORD=password \
    POSTGRES_USER=user

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Run Maven to build the application (skipping tests)
RUN mvn clean package -DskipTests

# Copy the JAR file to the final location
# Adjust the path if the output JAR name is different
RUN cp target/*.jar application.jar

# Set the maximum heap size for Java
ENV JAVA_OPTS="-Xmx2048M"

# Expose the port that the application listens on
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/application.jar"]
