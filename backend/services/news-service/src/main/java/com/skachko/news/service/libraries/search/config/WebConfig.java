package com.skachko.news.service.libraries.search.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.news.service.libraries.mvc.PathVariablesParamResolver;
import com.skachko.news.service.libraries.search.CriteriaParamResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CriteriaParamResolver(objectMapper));
        resolvers.add(new PathVariablesParamResolver());
    }


}
