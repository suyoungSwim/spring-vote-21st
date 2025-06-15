package com.ceos.spring_vote_21st.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("API 문서").version("v1"))
                .addSecurityItem(new SecurityRequirement().addList("AccessToken( Bearer없이 토큰만 넣어주세요:) )"))
                .components(new Components()
                        .addSecuritySchemes("AccessToken( Bearer없이 토큰만 넣어주세요:) )", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT")))

                .paths(new Paths().addPathItem("/api/v1/auth/signin", loginPathItem))
                // Swagger-UI Try it out → "/api/..." 상대 경로로 호출
                .addServersItem(new Server().url("/"));
    }
}
