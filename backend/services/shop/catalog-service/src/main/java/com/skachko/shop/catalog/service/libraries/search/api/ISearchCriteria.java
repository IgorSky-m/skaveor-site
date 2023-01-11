package com.skachko.shop.catalog.service.libraries.search.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.skachko.shop.catalog.service.libraries.search.SearchCriteria;
import org.springframework.data.domain.Sort;

import java.util.Map;

@JsonDeserialize(as = SearchCriteria.class)
public interface ISearchCriteria {
    ISearchPredicate getSearchPredicate();
    Map<String, Sort.Direction> getSortFields();
}
