package com.skachko.shop.catalog.service.libraries.mvc.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class APathParamContainer<T> implements IPathParamContainer<T>{

    private final Map<String, T> parameters;
    private final Function<String, T> func;

    public APathParamContainer(Function<String, T> func) {
        this.func = func;
        this.parameters = new HashMap<>();
    }

    public APathParamContainer(Map<String, T> parameters, Function<String, T> func) {
        this.parameters = new HashMap<>(parameters);
        this.func = func;
    }

    @Override
    public T getParam(String name) {
        return parameters.get(name);
    }

    @Override
    public void add(String key, T param) {
        this.parameters.put(key, param);
    }

    @Override
    public Function<String, T> getConvertFunc() {
        return func;
    }

    @Override
    public void addAll(Map<String, T> paramMap) {
        parameters.putAll(paramMap);
    }
}
