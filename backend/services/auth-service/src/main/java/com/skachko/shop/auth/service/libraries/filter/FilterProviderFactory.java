package com.skachko.shop.auth.service.libraries.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.skachko.shop.auth.service.libraries.filter.api.IFilterProviderFactory;

import java.util.HashMap;
import java.util.Map;


public class FilterProviderFactory implements IFilterProviderFactory {

    private final Map<Integer, FilterProvider> filters;
    private final FilterProvider defaultFilterProvider;

    public FilterProviderFactory(FilterProvider defaultFilterProvider){
        filters = new HashMap<>();
        this.defaultFilterProvider = defaultFilterProvider;
    }

    public FilterProviderFactory(Map<Integer, FilterProvider> filters, FilterProvider defaultFilterProvider) {
        this.filters = filters;
        this.defaultFilterProvider = defaultFilterProvider;
    }

    public void addFilterProvider(FieldViewFilterProvider provider){
        filters.put(provider.getViewLevel(), provider);
    }

    public FilterProvider getFilterProvider(int viewLevel){
        return filters.getOrDefault(viewLevel, defaultFilterProvider);
    }

    @Override
    public FilterProvider getDefaultFilterProvider() {
        return defaultFilterProvider;
    }

}
