# Build stage
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Dar permisos de ejecución al wrapper
RUN chmod +x mvnw

# Descargar dependencias (cacheado si pom.xml no cambia)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar aplicación
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy AS final
WORKDIR /app

# Instalar curl para health checks
RUN apt-get update && \
    apt-get install -y --no-install-recommends curl && \
    rm -rf /var/lib/apt/lists/*

# Crear usuario no-root para mayor seguridad
RUN groupadd -r spring && useradd -r -g spring spring

# Crear directorio de logs con permisos
RUN mkdir -p /var/log/core-service && \
    chown -R spring:spring /var/log/core-service

# Copiar JAR desde build stage
COPY --from=build /app/target/*.jar app.jar

# Cambiar propiedad de archivos
RUN chown -R spring:spring /app

# Cambiar a usuario no-root
USER spring

# Exponer puerto
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD curl -f http://localhost:8081/actuator/health || exit 1

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS="-Xmx512m -Xms256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
