#!/usr/bin/env zsh
set -e

# 1) Ir al proyecto
cd "$HOME/Desktop/GRHApp-9-3"

# 2) Usar Java 17
export JAVA_HOME="$("/usr/libexec/java_home" -v 17)"
export PATH="$JAVA_HOME/bin:$PATH"

# 3) Pedir datos de conexión de Railway (los ves en MySQL → Data → Connection → "Public Proxy")
HOST="maglev.proxy.rlwy.net"
DB="grhdatabase"

print -n "Puerto de Railway (ej. 42188): "
read PORT
print -n "Usuario MySQL (root o tu usuario, ej. grhapp): "
read USER
stty -echo; print -n "Password MySQL: "; read PASS; stty echo; echo

# 4) Variables de entorno para Spring/Flyway
export SPRING_PROFILES_ACTIVE="prod"
export SPRING_DATASOURCE_URL="jdbc:mysql://${HOST}:${PORT}/${DB}?serverTimezone=UTC&allowPublicKeyRetrieval=true&sslMode=PREFERRED&createDatabaseIfNotExist=true"
export SPRING_DATASOURCE_USERNAME="$USER"
export SPRING_DATASOURCE_PASSWORD="$PASS"
export SPRING_JPA_HIBERNATE_DDL_AUTO="none"

export SPRING_FLYWAY_URL="$SPRING_DATASOURCE_URL"
export SPRING_FLYWAY_USER="$USER"
export SPRING_FLYWAY_PASSWORD="$PASS"
export SPRING_FLYWAY_LOCATIONS="classpath:db/migration"
export SPRING_FLYWAY_CONNECT_RETRIES="10"

# 5) Compilar y arrancar
mvn -DskipTests clean package
mvn -DskipTests spring-boot:run
