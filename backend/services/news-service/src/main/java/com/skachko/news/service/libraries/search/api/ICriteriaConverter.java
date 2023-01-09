package com.skachko.news.service.libraries.search.api;

public interface ICriteriaConverter<T> {
    T convert(ISearchCriteria criteria);
}
