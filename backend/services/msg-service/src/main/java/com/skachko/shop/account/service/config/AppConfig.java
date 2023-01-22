package com.skachko.shop.account.service.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig implements InitializingBean {

    private final String httpsProtocols;

    public AppConfig(@Value("${msg.https.protocols}") String httpsProtocols){
        this.httpsProtocols = httpsProtocols;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("https.protocols", this.httpsProtocols);
    }
}
