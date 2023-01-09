package com.skachko.shop.catalog.service.libraries.search.annotations;

import com.skachko.shop.catalog.service.libraries.search.api.ISearchCriteria;
import com.skachko.shop.catalog.service.libraries.search.SearchCriteria;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface ASearchCriteria {
    String paramName() default "criteria";
    Class<? extends ISearchCriteria> criteriaClass() default SearchCriteria.class;
}
