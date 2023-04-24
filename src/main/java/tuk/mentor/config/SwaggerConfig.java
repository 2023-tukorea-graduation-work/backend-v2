package tuk.mentor.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig{
    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Mento API Docs")
                        .description("API Docs for Mento")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Mento Team Repository")
                        .url("https://github.com/2023-tukorea-graduation-work"));
    }
}
