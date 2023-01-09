package com.skachko.shop.catalog.service.libraries.search.api;

public interface ICriteriaConverter<T> {
    T convert(ISearchCriteria criteria);
}
