package com.skachko.shop.warehouse.service.libraries.search.api;

public interface ICriteriaConverter<T> {
    T convert(ISearchCriteria criteria);
}
