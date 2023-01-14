package com.skachko.shop.order.service.libraries.mvc.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ICUDService<T, ID> {

    T save(T t);
    T save(T t, Date dtCreate);
    List<T> save(Collection<T> list);
    List<T> save(Collection<T> list, Date dtCreate);

    T  update(IPathParamContainer<ID> paramContainer, Date version, T t);
    T  update(UUID id, Date version, T t, Date dtUpdate);
    T  update(UUID id, Date version, T t);


    T delete(IPathParamContainer<ID> paramContainer, Date version);
    T delete(UUID id, Date version, Date dtDelete);
    T delete(UUID id, Date version);
}
