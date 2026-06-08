package com.pase.transport.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI transportApiOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Transport Orders API")
                        .description("API para gestión de órdenes de transporte y conductores")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ruben Diaz")
                                .email("diazb1@hotmail.com")));
    }
}
