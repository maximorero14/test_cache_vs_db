# Build stage: build the jar with all our resources
FROM maven:3.8.1-openjdk-17 as build
WORKDIR /app
ADD pom.xml .
RUN mvn dependency:go-offline
ADD . .
RUN mvn clean package -DskipTests

# Runtime stage: prepare the final image
FROM amazoncorretto:17-alpine as runtime
WORKDIR /app
COPY --from=build /app/target/application.jar ./
COPY cuentas.csv ./
COPY cuentas.csv /app/cuentas.csv
USER 1000:1000
ENTRYPOINT ["java", "-jar", "application.jar"]