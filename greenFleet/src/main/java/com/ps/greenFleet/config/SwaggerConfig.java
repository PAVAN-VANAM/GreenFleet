package com.ps.greenfleet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI greenFleetOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GreenFleet API")
                        .description("API documentation for GreenFleet Logistics Platform")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("GreenFleet Dev Team")
                                .email("support@greenfleet.com")
                                .url("https://greenfleet.com")
                        )
                );
    }
}
