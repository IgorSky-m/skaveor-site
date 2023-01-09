package com.skachko.news.service.libraries.search.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.skachko.news.service.libraries.search.SearchCriteria;

import java.util.Map;

@JsonDeserialize(as = SearchCriteria.class)
public interface ISearchCriteria {
    ISearchPredicate getSearchPredicate();
    Map<String, ESort> getSortFields();
}
