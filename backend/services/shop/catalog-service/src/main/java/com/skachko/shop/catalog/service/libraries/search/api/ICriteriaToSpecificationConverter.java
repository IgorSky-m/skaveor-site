package com.skachko.shop.catalog.service.libraries.search.api;

import org.springframework.data.jpa.domain.Specification;

public interface ICriteriaToSpecificationConverter<T> extends ICriteriaConverter<Specification<T>> {
}
