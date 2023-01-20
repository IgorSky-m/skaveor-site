package com.skachko.shop.news.service.libraries.search.api;

public interface ICriteriaConverter<T> {
    T convert(ISearchCriteria criteria);
}
