package com.skachko.shop.order.service.libraries.search.converter.api;

import com.skachko.shop.order.service.libraries.search.converter.Column;

public interface IParamValueConverter {
    
    <K> K convertAndCast(Object value, Column column);
}
