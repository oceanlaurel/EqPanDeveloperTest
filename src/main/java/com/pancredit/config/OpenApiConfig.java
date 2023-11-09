package com.pancredit.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("spring-api")
                .pathsToMatch("/ApiServlet/**") // Ensure this matches our controller's RequestMapping
                .build();
    }
}