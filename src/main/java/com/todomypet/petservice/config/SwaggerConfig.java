package com.todomypet.petservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().title("펫 서비스 API 문서")
                .version("v1.0")
                .description("펫 서비스 API 명세입니다.");

        return new OpenAPI()
                .info(info)
                .schemaRequirement("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .scheme("bearer").bearerFormat(("JWT")));
    }
}
