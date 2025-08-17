//package sn.unchk.librarymanagement.config;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfiguration {
//
//    @Value("${spring.application.name}")
//    private String APPLICATION_NAME;
//
//    public static final String SCHEME = "bearer";
//    public static final String BEARER_FORMAT = "JWT";
//    public static final String SECURITY_NAME = "Bearer";
//
//    @Bean
//    public OpenAPI AccessOpenAPI(){
//        SecurityScheme securityScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .scheme(SCHEME).bearerFormat(BEARER_FORMAT);
//
//        SecurityRequirement securityRequirement = new SecurityRequirement();
//
//        return new OpenAPI()
//                .info(new Info().title(APPLICATION_NAME).version("1.0"))
//                .addSecurityItem(securityRequirement)
//                .components(new Components().addSecuritySchemes(SECURITY_NAME, securityScheme));
//    }
//}
