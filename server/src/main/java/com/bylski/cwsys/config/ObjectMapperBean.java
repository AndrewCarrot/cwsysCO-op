package com.bylski.cwsys.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperBean {
    @Bean
    public ObjectMapper objectMapper(){
        //register the JavaTimeModule() to make Jackson support Java 8 date time APIs.
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
