package com.ps.smallvehicles.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ride & Pool API")
                        .description("API documentation for User Entity (Ride/Pool/Bike system)")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Pavan")
                                .email("pavan@example.com"))
                        .license(new License().name("Apache 2.0")));
    }
}
