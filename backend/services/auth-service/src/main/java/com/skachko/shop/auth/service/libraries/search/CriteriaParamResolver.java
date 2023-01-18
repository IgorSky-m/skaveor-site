package com.skachko.shop.auth.service.libraries.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.shop.auth.service.libraries.search.annotations.ASearchCriteria;
import com.skachko.shop.auth.service.libraries.search.api.ISearchCriteria;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CriteriaParamResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper mapper;

    public CriteriaParamResolver(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ASearchCriteria.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        ASearchCriteria criteria = parameter.getParameterAnnotation(ASearchCriteria.class);
        if (criteria == null) {
            return null;
        }

        String jsonString = webRequest.getParameter(criteria.paramName());

        ISearchCriteria resolvedCriteria;

        if (jsonString != null){

            resolvedCriteria = mapper.readValue(jsonString, criteria.criteriaClass());

        } else {

            resolvedCriteria = criteria.criteriaClass()
                    .getDeclaredConstructor()
                    .newInstance();

        }

        return resolvedCriteria;
    }
}
