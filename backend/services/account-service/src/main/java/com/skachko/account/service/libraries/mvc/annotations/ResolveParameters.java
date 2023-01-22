package com.skachko.account.service.libraries.mvc.annotations;

import com.skachko.account.service.libraries.mvc.api.IPathParamContainer;
import com.skachko.account.service.libraries.mvc.UuidPathParamContainer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResolveParameters {
    String suffix() default IPathParamContainer.BASE_ID_SUFFIX;
    Class<? extends IPathParamContainer> paramContainer() default UuidPathParamContainer.class;

}
