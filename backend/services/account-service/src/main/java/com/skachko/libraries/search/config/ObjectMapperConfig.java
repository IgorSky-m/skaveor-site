package com.skachko.libraries.search.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder){
        builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return builder.build();
    }
}
