package rest.api.cardinity.taskmanager.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

    @Value("${api.version}")
    private String apiVersion;

    public static String AUTHORIZATION_HEADER = "AuthenticationToken";
    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";

    @Bean
    public Docket api() {
        List<ResponseMessage> globalResponseMessages = this.getGlobalResponseMessages();
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .consumes(getContentType())
                .produces(getContentType())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalResponseMessages)
                .globalResponseMessage(RequestMethod.POST, globalResponseMessages)
                .globalResponseMessage(RequestMethod.DELETE, globalResponseMessages)
                .globalResponseMessage(RequestMethod.PUT, globalResponseMessages)
                .globalResponseMessage(RequestMethod.PATCH, globalResponseMessages)
                .globalResponseMessage(RequestMethod.OPTIONS, globalResponseMessages);
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private Set<String> getContentType() {
        return new HashSet<>(Collections.singleton(MediaType.APPLICATION_JSON_VALUE));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Cardinity Project Management REST API")
                .description("Cardinity Project Management REST API")
                .version(apiVersion)
                .contact(new Contact("Developed by Dipanjal Maitra", "https://github.com/dipanjal", "dipanjalmaitra@gmail.com"))
                .license("A sample rest api project for cardinity")
                .build();
    }

    private List<ResponseMessage> getGlobalResponseMessages() {
        return new ArrayList<>(Arrays.asList(new ResponseMessageBuilder().code(ResponseCode.OPERATION_SUCCESSFUL.getCode()).message(ResponseCode.OPERATION_SUCCESSFUL.toString()).responseModel(new ModelRef(ResponseCode.OPERATION_SUCCESSFUL.toString())).build(),
                new ResponseMessageBuilder().code(ResponseCode.RECORD_NOT_FOUND.getCode()).message(ResponseCode.RECORD_NOT_FOUND.toString()).responseModel(new ModelRef(ResponseCode.RECORD_NOT_FOUND.toString())).build(),
                new ResponseMessageBuilder().code(ResponseCode.UNAUTHORIZED.getCode()).message(ResponseCode.UNAUTHORIZED.toString()).responseModel(new ModelRef(ResponseCode.UNAUTHORIZED.toString())).build(),
                new ResponseMessageBuilder().code(ResponseCode.BAD_REQUEST.getCode()).message(ResponseCode.BAD_REQUEST.toString()).responseModel(new ModelRef(ResponseCode.BAD_REQUEST.toString())).build(),
                new ResponseMessageBuilder().code(ResponseCode.RUNTIME_ERROR.getCode()).message(ResponseCode.RUNTIME_ERROR.toString()).responseModel(new ModelRef(ResponseCode.RUNTIME_ERROR.toString())).build(),
                new ResponseMessageBuilder().code(ResponseCode.REMOTE_ERROR.getCode()).message(ResponseCode.REMOTE_ERROR.toString()).responseModel(new ModelRef(ResponseCode.REMOTE_ERROR.toString())).build(),
                new ResponseMessageBuilder().code(ResponseCode.INTERNAL_SERVER_ERROR.getCode()).message(ResponseCode.INTERNAL_SERVER_ERROR.toString()).responseModel(new ModelRef(ResponseCode.INTERNAL_SERVER_ERROR.toString())).build()
        ));
    }
}
