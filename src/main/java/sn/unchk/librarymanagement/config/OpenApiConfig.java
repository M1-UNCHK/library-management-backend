package sn.unchk.librarymanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Library Management REST API", version = "1.0",
        description = "Library Management REST API Documentation ...",
        contact = @Contact(name = "Talents Consulting", email = "developer@talentsconsult.com")),
        security = {@SecurityRequirement(name = "BearerToken")}
)

@SecurityScheme(name = "BearerToken", type = SecuritySchemeType.HTTP,
        scheme = "Bearer", bearerFormat = "JWT")
public class OpenApiConfig {
    @Value("${application.url}")
    private String url;


    @Bean
    public OpenAPI config() {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription("Server DEV URL");

        return new OpenAPI().servers(List.of(server));
    }
}
