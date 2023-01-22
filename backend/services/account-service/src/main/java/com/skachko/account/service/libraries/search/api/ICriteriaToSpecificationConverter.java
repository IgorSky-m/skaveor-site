package com.skachko.account.service.libraries.search.api;

import org.springframework.data.jpa.domain.Specification;

public interface ICriteriaToSpecificationConverter<T> extends ICriteriaConverter<Specification<T>> {
}
