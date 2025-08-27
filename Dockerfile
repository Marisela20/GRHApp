# -------- Build (Java 17 + Maven) ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B -q -DskipTests clean package

# -------- Run (JRE 17) ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/target/*.war /app/app.war
EXPOSE 8080
CMD ["sh","-c","java -jar /app/app.war --server.port=${PORT:-8080}"]
# -------- Build (Java 17 + Maven) ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B -q -DskipTests clean package

# -------- Run (JRE 17) ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/target/*.war /app/app.war
EXPOSE 8080
CMD ["sh","-c","java -jar /app/app.war --server.port=${PORT:-8080}"]

