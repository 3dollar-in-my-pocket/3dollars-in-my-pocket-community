package com.threedollar.config.swagger;

import com.threedollar.config.resolver.RequestApiKey;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String key = "X-Community-Api-Key";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes(key, new SecurityScheme().type(Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .name(key)))
            .addSecurityItem(new SecurityRequirement().addList(key)) // 모든 API에 적용
            .info(new Info().title("ThreeDollar Community API")
                .description("ThreeDollar API 문서입니다.")
                .version("v0.0.9"));
    }

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(RequestApiKey.class);
    }

}
