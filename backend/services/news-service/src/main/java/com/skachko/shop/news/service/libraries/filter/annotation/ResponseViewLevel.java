package com.skachko.shop.news.service.libraries.filter.annotation;

import com.skachko.shop.news.service.libraries.filter.ViewConstraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseViewLevel {
    int value() default ViewConstraints.ViewLevel.DETAILED;
}
