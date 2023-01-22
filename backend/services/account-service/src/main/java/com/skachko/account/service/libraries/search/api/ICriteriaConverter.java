package com.skachko.account.service.libraries.search.api;

public interface ICriteriaConverter<T> {
    T convert(ISearchCriteria criteria);
}
