package com.skachko.shop.auth.service.libraries.filter;

import com.skachko.shop.auth.service.libraries.filter.annotation.ResponseViewLevel;
import com.skachko.shop.auth.service.libraries.filter.api.IFilterProviderFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

/**
 * advice, с помощью которого подкладываем фильтр перед записью тела
 */
@ControllerAdvice
public final class EssenceResponseBodyAdvice
        extends AbstractMappingJacksonResponseBodyAdvice {

    private final IFilterProviderFactory ff;

    public EssenceResponseBodyAdvice(IFilterProviderFactory ff) {
        this.ff = ff;
    }

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType)
                && returnType.hasMethodAnnotation(ResponseViewLevel.class);
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue container, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

        ResponseViewLevel mode = returnType.getMethodAnnotation(ResponseViewLevel.class);

        if (mode != null) {
            container.setFilters(ff.getFilterProvider(mode.value()));
        }

    }
}
