# ---- Stage 1: Extract layers ----
FROM eclipse-temurin:21-jre-alpine AS builder

WORKDIR /app
COPY target/webserver-0.0.1-SNAPSHOT.jar app.jar

# Extract JAR into layers
RUN java -Djarmode=layertools -jar app.jar extract

# ---- Stage 2: Final lean image ----
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy only extracted layers from Stage 1
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]