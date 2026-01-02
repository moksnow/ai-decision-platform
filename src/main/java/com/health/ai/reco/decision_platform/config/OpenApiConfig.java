package com.health.ai.reco.decision_platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author m.khandan
 * 1/2/2026
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Decision Platform API - Health Case Study")
                        .version("1.0")
                        .description("API endpoints for AI request governance and risk evaluation"));
    }
}