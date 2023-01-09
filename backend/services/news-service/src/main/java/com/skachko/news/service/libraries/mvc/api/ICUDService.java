package com.skachko.news.service.libraries.mvc.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ICUDService<T, ID> {

    T save(T t);
    List<T> save(Collection<T> list);

    T  update(IPathParamContainer<ID> paramContainer, Date version, T t);

    T delete(IPathParamContainer<ID> paramContainer, Date version);
}
