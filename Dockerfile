FROM maven:3-amazoncorretto-21 AS builder

WORKDIR /opt/app
COPY . .
RUN mvn clean package

FROM amazoncorretto:21-alpine AS runtime

# Add a non-root user and switch to it
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

WORKDIR /opt/app

COPY --from=builder --chown=appuser:appgroup /opt/app/target/ .

ENTRYPOINT ["java","-jar","htmx-0.0.1-SNAPSHOT.jar"]