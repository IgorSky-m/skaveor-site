package com.skachko.shop.auth.service.libraries.search.api;

import org.springframework.data.domain.Sort;

import java.util.Optional;

@FunctionalInterface
public interface ICriteriaSortExtractor {
    Optional<Sort> getSort(ISearchCriteria criteria);
}
