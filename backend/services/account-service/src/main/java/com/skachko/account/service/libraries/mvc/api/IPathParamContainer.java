package com.skachko.account.service.libraries.mvc.api;

import java.util.Map;
import java.util.function.Function;

public interface IPathParamContainer<T> {
    String BASE_ID_SUFFIX = "_key";

    T getParam(String name);
    void add(String key, T param);
    void addAll(Map<String, T> paramMap);

    Function<String, T> getConvertFunc();
}
