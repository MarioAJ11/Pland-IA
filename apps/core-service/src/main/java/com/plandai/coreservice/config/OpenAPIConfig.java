package com.plandai.coreservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de SpringDoc OpenAPI (Swagger).
 * 
 * Define la información que aparece en la documentación de la API:
 * - Título, descripción, versión
 * - Información de contacto
 * - Servidores disponibles
 * 
 * Accesible en: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pland-IA Core Service API")
                        .version("1.0.0")
                        .description("""
                                API REST para la gestión de workspaces, proyectos y tareas.
                                
                                **Pland-IA** es una plataforma de gestión de tareas con IA,
                                diseñada para uso global con arquitectura de microservicios.
                                
                                ## Características principales:
                                - 🏢 **Workspaces**: Espacios de trabajo para organizar proyectos
                                - 📂 **Projects**: Proyectos dentro de cada workspace
                                - ✅ **Tasks**: Tareas con estados, prioridades y fechas límite
                                
                                ## Arquitectura:
                                - **Auth Service** (.NET 8): Autenticación con JWT
                                - **Core Service** (Spring Boot 3.5): Gestión de tareas (este servicio)
                                - **Pantry Service** (futuro): Gestión de inventarios
                                
                                ## Clientes soportados:
                                - 🖥️ Desktop (Tauri)
                                - 🌐 Web (React)
                                - 📱 Móvil (futuro)
                                """)
                        .contact(new Contact()
                                .name("Mario AJ")
                                .email("mario@pland-ia.com")
                                .url("https://github.com/MarioAJ11/Pland-IA"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo local"),
                        new Server()
                                .url("https://api.pland-ia.com")
                                .description("Servidor de producción (futuro)")
                ));
    }
}
