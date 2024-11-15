package com.bylski.cwsys.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080/api");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("10xDev");
        myContact.setEmail("hugo9987@wp.pl");

        Info information = new Info()
                .title("System Karnetowy Pajonk 3000 Ultra Edition")
                .version("1.0")
                .description("Później się to zateguje")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
