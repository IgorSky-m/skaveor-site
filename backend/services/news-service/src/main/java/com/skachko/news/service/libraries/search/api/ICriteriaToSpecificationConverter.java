package com.skachko.news.service.libraries.search.api;

import org.springframework.data.jpa.domain.Specification;

public interface ICriteriaToSpecificationConverter<T> extends ICriteriaConverter<Specification<T>> {
}