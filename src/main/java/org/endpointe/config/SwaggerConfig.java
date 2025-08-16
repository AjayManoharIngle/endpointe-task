package org.endpointe.config;

import java.util.List;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
    public static final String SECURITY_SCHEME_NAME = "BearerAuth";
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    
    @Autowired
    private SecurityConfig securityConfig;

	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Users API").version("1.0").description("API documentation for User management"))        
        	.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
                new SecurityScheme()
                    .name(SECURITY_SCHEME_NAME)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }
	
	@Bean
    public OpenApiCustomizer securityExclusionCustomiser() {
        return openApi -> {
            Paths paths = openApi.getPaths();
            if (paths != null) {
                paths.forEach((path, pathItem) -> {
                    if (isPublicApi(path)) {
                        removeSecurity(pathItem);
                    }
                });
            }
        };
    }

    private boolean isPublicApi(String path) {
        for (String publicUrl : securityConfig.getPUBLIC_URLS()) {
            if (pathMatcher.match(publicUrl, path)) {
                return true;
            }
        }
        return false;
    }

    private void removeSecurity(PathItem pathItem) {
        pathItem.readOperationsMap().forEach((method, operation) -> {
            if (operation != null) {
                operation.setSecurity(List.of());
            }
        });
    }
}
