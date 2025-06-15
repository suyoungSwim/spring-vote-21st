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
                // Swagger-UI Try it out → "/api/..." 상대 경로로 호출
                .addServersItem(new Server().url("/"));
    }
}
