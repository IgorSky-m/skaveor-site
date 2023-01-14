package com.skachko.shop.payment.service.libraries.search;

import com.skachko.shop.payment.service.libraries.search.api.EComparisonOperator;
import com.skachko.shop.payment.service.libraries.search.api.ISearchExpression;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchExpression implements ISearchExpression {

    private String field;
    private EComparisonOperator comparisonOperator;
    private Object[] values;

}
