package com.skachko.gateway.config;

import feign.Logger;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public  class FeignConfig {

    @Bean
    Logger.Level feignLevel() {
        // Record all
        return Logger.Level.FULL;
    }

    @Bean
    public Decoder feignDecoder() {
        return  new ResponseEntityDecoder( new SpringDecoder(feignHttpMessageConverter()));
    }

    public ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new CustomMappingJackson2HttpMessageConverter());
        return () -> httpMessageConverters;
    }

    public static class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        CustomMappingJackson2HttpMessageConverter(){
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8" ));
            mediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8" ));
            mediaTypes.add(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
