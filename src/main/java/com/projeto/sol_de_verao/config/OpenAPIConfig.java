package com.projeto.sol_de_verao.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Sol de Verão")
                .version("v1")
                .description("Sol de Verão")
                .termsOfService("https://github.com/pedrobatistadev")
                .license(new License().name("Apache 2.0").url("https://github.com/pedrobatistadev"))
        );
    }

}
