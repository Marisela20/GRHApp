# ============ STAGE 1: build del FRONTEND (Vite) ============
FROM node:lts-alpine AS frontend-build
WORKDIR /app/frontend
# Instalar dependencias del frontend
COPY frontend/package*.json ./
RUN npm ci
# Copiar código del frontend y compilar
COPY frontend/ .
# Si usas una API externa, puedes inyectar variable en build time:
# ARG VITE_API_URL
# ENV VITE_API_URL=$VITE_API_URL
RUN npm run build

# ============ STAGE 2: build del BACKEND (Maven) ============
FROM maven:3.9.6-eclipse-temurin-11 AS backend-build
WORKDIR /workspace
# Copiar descriptores y bajar dependencias en cache
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
# Copiar código del backend
COPY src ./src
# Copiar el build del frontend dentro de los recursos estáticos del backend
RUN mkdir -p src/main/resources/static
COPY --from=frontend-build /app/frontend/dist/ ./src/main/resources/static/
# Empaquetar la app (el JAR ya incluye los estáticos)
RUN mvn -DskipTests clean package

# ============ STAGE 3: runtime liviano ============
FROM eclipse-temurin:11-jre
# ============ STAGE 3: runtime (puerto dinámico) ============
FROM eclipse-temurin:11-jre
WORKDIR /app
COPY --from=backend-build /workspace/target/*.jar app.jar

# Importante: NO fijes ENV PORT=8080 ni EXPOSE 8080.
# Escucha en el puerto que Railway inyecta en $PORT.
CMD ["sh","-c","java -Dserver.port=$PORT -Dserver.address=0.0.0.0 -jar app.jar"]
WORKDIR /app
# Copiar JAR final
COPY --from=backend-build /workspace/target/*.jar app.jar
# Railway establece $PORT, lo usamos para server.port
ENV PORT=8080
EXPOSE 8080
CMD ["bash", "-lc", "java -Dserver.port=${PORT} -jar app.jar"]

