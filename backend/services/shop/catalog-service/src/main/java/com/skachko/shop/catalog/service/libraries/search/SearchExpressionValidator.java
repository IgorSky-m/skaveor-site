package com.skachko.shop.catalog.service.libraries.search;

import com.skachko.shop.catalog.service.libraries.search.api.EComparisonOperator;
import com.skachko.shop.catalog.service.libraries.search.api.ISearchExpression;
import com.skachko.shop.catalog.service.libraries.search.api.ISearchExpressionValidator;
import org.springframework.stereotype.Component;

@Component
public class SearchExpressionValidator implements ISearchExpressionValidator {

    @Override
    public void accept(ISearchExpression expression) {
        if (expression == null) {
            //TODO Exception
            throw new IllegalArgumentException("expression Cant' be null");
        }

        if (isEmpty(expression.getField())) {
            //TODO Exception
            throw new IllegalArgumentException("field Cant' be null");
        }

        if (expression.getComparisonOperator() == null) {
            //TODO Exception
            throw new IllegalArgumentException("Comparison Operator Cant' be null");
        }

        int count = expression.getValues() == null ? 0 : expression.getValues().length;

        EComparisonOperator comparisonOperator = expression.getComparisonOperator();

        if (!comparisonOperator.AMOUNT.check(count)){
            //TODO Exception
            throw new IllegalArgumentException("illegal operators count: with [0] method may be [0] operators");
        };
    }

    public boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }
}
