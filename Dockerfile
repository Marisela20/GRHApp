# ============ STAGE 1: build del FRONTEND (Vite) ============
FROM node:20-alpine AS frontend-build
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm ci
COPY frontend/ .
RUN npm run build

# ============ STAGE 2: build del BACKEND (Maven) ============
FROM maven:3.9.6-eclipse-temurin-11 AS backend-build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
# Copiar build del frontend como estáticos del backend
RUN mkdir -p src/main/resources/static
COPY --from=frontend-build /app/frontend/dist/ ./src/main/resources/static/
RUN mvn -DskipTests clean package

# ============ STAGE 3: runtime ============
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=backend-build /workspace/target/*.jar /app/app.jar

# Importante: forzamos perfil y puerto dinámico de Railway
CMD ["sh","-c","exec java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod} -Dserver.port=${PORT} -Dserver.address=0.0.0.0 -jar /app/app.jar"]

