package com.skachko.news.service.libraries.filter;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;

public class FieldViewFilterProvider extends FilterProvider {

    private final byte viewLevel;

    private final PropertyFilter filter;

    public FieldViewFilterProvider(byte viewLevel) {
        this.viewLevel = viewLevel;
        this.filter = new ViewFilter(viewLevel);
    }

    @Deprecated
    @Override
    public BeanPropertyFilter findFilter(Object filterId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
        if (ViewConstraints.FIELD_VIEW_FILTER_ID.equals(filterId)) {
            return filter;
        }
        return null;
    }

    public int getViewLevel() {
        return viewLevel;
    }
}
