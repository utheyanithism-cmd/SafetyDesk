package com.cts.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI safetyDeskOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SafetyDesk API")
                        .description("Employee Health, Safety & Compliance Management System")
                        .version("v1")
                        .contact(new Contact().name("SafetyDesk")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

   @Bean
    public GroupedOpenApi coreModulesApiGroup() {
        return GroupedOpenApi.builder()
                .group("Core Modules (Auth, Incidents & Audit)")
                .pathsToMatch(
                        "/api/auth/**",
                        "/api/incidents/**",
                        "/api/audit/**",
                        "/api/users/**"
                )
                .build();
    }

  @Bean
    public GroupedOpenApi allApiGroup() {
        return GroupedOpenApi.builder()
                .group("All System Endpoints")
                .pathsToMatch("/api/**")
                .build();
    }
}