package sn.edu.isep.dbe.docsEtConfig.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI configureSwagger(){
        return new OpenAPI()
                .info(
                        new Info().title("Demo documentation et configuration")
                                .description("montre comment générer " +
                                        "une documentation OPEN API et " +
                                        "comment configurer une application " +
                                        "avec des profiles et des variables d'environnement")
                                .version("1.0")
                                .contact(new Contact()
                                        .email("ib20082003@gmail.com")
                                        .name("Ibrahima Diallo")
                                        .url("https://github.com/IDDEVELOPPEUR")

                                )
                        )
                ;

    }
}
