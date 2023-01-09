package com.skachko.shop.catalog.service.libraries.mvc;

import com.skachko.shop.catalog.service.libraries.mvc.annotations.ResolveParameters;
import com.skachko.shop.catalog.service.libraries.mvc.api.IPathParamContainer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathVariablesParamResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ResolveParameters.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        ResolveParameters annotation = parameter.getParameterAnnotation(ResolveParameters.class);

        if (annotation == null) {
            return null;
        }

        IPathParamContainer container = annotation.paramContainer()
                .getDeclaredConstructor()
                .newInstance();

        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String, String> attribute = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Map<String, Object> collect = attribute.entrySet().stream()
                .filter(e -> e.getKey().endsWith(annotation.suffix()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> container.getConvertFunc().apply(e.getValue())));

        container.addAll(collect);

        return container;
    }
}
