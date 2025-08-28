# ============ STAGE 1: build FRONT ============
FROM node:lts-alpine AS frontend-build
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm ci
COPY frontend/ .
RUN npm run build

# ============ STAGE 2: build BACK ============
FROM maven:3.9.6-eclipse-temurin-11 AS backend-build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mkdir -p src/main/resources/static
COPY --from=frontend-build /app/frontend/dist/ ./src/main/resources/static/
RUN mvn -DskipTests clean package

# ============ STAGE 3: runtime ============
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=backend-build /workspace/target/*.jar app.jar
EXPOSE 8080
# IMPORTANTE: sin ENV PORT=8080
ENTRYPOINT ["bash","-lc","echo PORT=${PORT}; java -Dserver.port=${PORT} -Dserver.address=0.0.0.0 -jar app.jar"]

