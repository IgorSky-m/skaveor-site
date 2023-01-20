package com.skachko.shop.order.service.libraries.search.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.skachko.shop.order.service.libraries.filter.EntityMixIn;
import com.skachko.shop.order.service.libraries.filter.api.IFilterProviderFactory;
import com.skachko.shop.order.service.support.jackson.serializers.DateDeserializer;
import com.skachko.shop.order.service.support.jackson.serializers.DateSerializer;
import com.skachko.shop.order.service.support.jackson.serializers.LocalDateDeserializer;
import com.skachko.shop.order.service.support.jackson.serializers.LocalDateSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Configuration
public class ObjectMapperConfig {


    @Bean
    public ObjectMapper objectMapper(
            Jackson2ObjectMapperBuilder builder,
            @Value("${com.skachko.libraries.search.config.localDateTime.pattern}") String pattern,
            IFilterProviderFactory factory
    ){
        builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        builder.serializerByType(Date.class, new DateSerializer());
        builder.serializerByType(LocalDate.class, new LocalDateSerializer(pattern));
        builder.deserializerByType(Date.class, new DateDeserializer());
        builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(pattern));
        builder.featuresToEnable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        builder.filters(factory.getDefaultFilterProvider());
        builder.mixIn(Object.class, EntityMixIn.class);
        return builder.build();
    }
}
