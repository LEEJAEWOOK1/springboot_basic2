package com.ex01.basic.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer");
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Quiz OpenAPI")
                                .description("스웨거 실습입니다")
                                .version("v1.0.0")
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080")
                                .description("로컬용 서버 주소")
                ))
                .components(
                        new Components().addSecuritySchemes("JWT",securityScheme)
                );
    }
}
