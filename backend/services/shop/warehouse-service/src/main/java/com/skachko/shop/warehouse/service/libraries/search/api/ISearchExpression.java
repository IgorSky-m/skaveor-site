package com.skachko.shop.warehouse.service.libraries.search.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.skachko.shop.warehouse.service.libraries.search.SearchExpression;

@JsonDeserialize(as = SearchExpression.class)
public interface ISearchExpression {

    String getField();

    EComparisonOperator getComparisonOperator();

    Object[] getValues();

    static ISearchExpression of(final String field, EComparisonOperator comparisonOperator, Object... values) {
        return new ISearchExpression() {
            @Override
            public String getField() {
                return field;
            }

            @Override
            public EComparisonOperator getComparisonOperator() {
                return comparisonOperator;
            }

            @Override
            public Object[] getValues() {
                return values;
            }
        };
    }
}
