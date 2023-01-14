package com.skachko.shop.order.service.libraries.filter.config;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.skachko.shop.order.service.libraries.filter.FieldViewFilterProvider;
import com.skachko.shop.order.service.libraries.filter.FilterProviderFactory;
import com.skachko.shop.order.service.libraries.filter.ViewConstraints;
import com.skachko.shop.order.service.libraries.filter.api.IFilterProviderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public IFilterProviderFactory filterProviderFactory(){

        IFilterProviderFactory factory = new FilterProviderFactory(
                new SimpleFilterProvider()
                        .setDefaultFilter(SimpleBeanPropertyFilter.serializeAll())
        );

        factory.addFilterProvider(new FieldViewFilterProvider(ViewConstraints.ViewLevel.BASE));
        factory.addFilterProvider(new FieldViewFilterProvider(ViewConstraints.ViewLevel.TABLE));
        factory.addFilterProvider(new FieldViewFilterProvider(ViewConstraints.ViewLevel.DETAILED));

        return factory;
    }
}
