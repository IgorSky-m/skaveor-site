package com.skachko.account.service.libraries.search.annotations;

import com.skachko.account.service.libraries.search.SearchCriteria;
import com.skachko.account.service.libraries.search.api.ISearchCriteria;

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
