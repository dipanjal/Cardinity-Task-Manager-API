package rest.api.cardinity.taskmanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dipanjal
 * @since 2/13/2021
 */
@Configuration
public class OpenApiConfiguration {

    @Value("${api.version}")
    private String apiVersion;

    private final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public GroupedOpenApi api(){
        return GroupedOpenApi.builder()
                .group("Cardinity APIs")
                .pathsToExclude("/user/**", "/dummy/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        System.out.println("Custom API");
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(getSecurityComponents())
                .info(getApiInfo());
    }

    private Components getSecurityComponents(){
        return new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }

    private Info getApiInfo(){
        return new Info()
                .title("Cardinity Rest API")
                .version(apiVersion)
                .description("A RESTful API Service for Cardinity Project Management Application")
                .contact(getContactInfo())
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    }

    private Contact getContactInfo(){
        return new Contact()
                .name("Dipanjal Maitra")
                .email("dipanjalmaitra@gmail.com")
                .url("https://github.com/dipanjal");
    }
}
