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
# Copiamos POM primero para cachear dependencias
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
# Ahora el código
COPY src ./src
# Copiamos el build del frontend a resources/static del backend
COPY --from=frontend-build /app/frontend/dist/ ./src/main/resources/static/
# Empaquetamos el jar
RUN mvn -DskipTests clean package

# ---------- Runtime ----------
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=backend-build /workspace/target/*.jar app.jar
EXPOSE 8080
# Arranque en prod (no dependas del Start Command de Railway)
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","app.jar"]

