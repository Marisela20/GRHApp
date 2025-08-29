# ---------- Frontend ----------
FROM node:lts-alpine AS frontend-build
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm ci
COPY frontend/ .
RUN npm run build

# ---------- Backend ----------
FROM maven:3.9.6-eclipse-temurin-11 AS backend-build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
COPY --from=frontend-build /app/frontend/dist/ ./src/main/resources/static/
RUN mvn -DskipTests clean package

# ---------- Runtime ----------
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=backend-build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","app.jar"]

# cache-bust 1756506090
