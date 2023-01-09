package com.skachko.libraries.mvc.api;

import com.skachko.libraries.search.api.ISearchCriteria;

import java.util.Collection;
import java.util.List;

public interface ICUDService<T, ID> {

    T save(T t);
    List<T> save(Collection<T> list);

    T  update(ID id, T t);

    T deleteById(ID id);
    List<T> deleteAllById(Collection<ID> ids);

    void deleteAll();

    long delete(ISearchCriteria criteria);
}
