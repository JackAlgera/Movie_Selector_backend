# gcloud builds submit --tag gcr.io/movieselector-326316/movie-selector-backend:latest

# Use the official maven/Java 11 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:latest as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn clean package -DskipTests

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM adoptopenjdk/openjdk11:latest

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/movie-selector-backend-*.jar /movie-selector-backend.jar

# Run the web service on container startup.
CMD ["java", "-jar", "/movie-selector-backend.jar"]
