# While using Kubernetes for deployment,
            # you truly do not rely on EXPOSE instruction in Dockerfile.
# It is conveniently ignored because you use the deployment file to expose your ports.
# You use the service configuration to expose your ports to external or internal clients.
#So you you define a desired port in Kubernetes using
            # the service configuration and not the dockerfile.
 #So the real port goes in the service configuration and not the dockerfile.

# Similarly, ENV PORT is used to set an environment variable called port with a specific value,
            # but it is actually not mandatory when using the Kubernetes
            # because you also define the port which is used by this particular application
            # to connect to other ports with other ports or with external clients or internal clients.

# So you you define a desired port in Kubernetes using
            # Service Configuration and not the dockerfile.

# So the real port goes in the Service Configuration and not the Dockerfile.

#FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-slim
#WORKDIR /opt
#COPY target/*.jar /opt/app.jar
#ENTRYPOINT exec java $JAVA_OPTS -jar app.jar


# Stage 1: Build the application
FROM openjdk:17-oracle AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM openjdk:17-oracle
WORKDIR /opt
COPY --from=builder /app/target/*.jar /opt/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]


