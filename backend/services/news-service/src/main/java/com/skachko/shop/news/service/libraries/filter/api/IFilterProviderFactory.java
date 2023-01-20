package com.skachko.shop.news.service.libraries.filter.api;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.skachko.shop.news.service.libraries.filter.FieldViewFilterProvider;

public interface IFilterProviderFactory {
    void addFilterProvider(FieldViewFilterProvider filterProvider);
    FilterProvider getFilterProvider(int id);

    FilterProvider getDefaultFilterProvider();


}
