package com.skachko.shop.auth.service.libraries.filter.annotation;

import com.skachko.shop.auth.service.libraries.filter.ViewConstraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldViewLevel {
    byte[] viewLevel() default ViewConstraints.ViewLevel.BASE;
    byte modeMatch() default ViewConstraints.MatchMode.HIERARCHICAL;
    boolean isHidden() default false;
}
