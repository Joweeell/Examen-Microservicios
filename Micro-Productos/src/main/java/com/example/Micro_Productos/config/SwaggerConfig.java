package com.example.Micro_Productos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*http://localhost:8081/swagger-ui/index.html*/
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API 2026 Registro de Productos")
                        .version("1.0")
                        .description("Documentación de la API para el registro de productos"));
    }
}
