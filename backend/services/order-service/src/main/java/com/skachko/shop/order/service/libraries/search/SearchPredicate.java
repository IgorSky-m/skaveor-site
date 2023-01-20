package com.skachko.shop.order.service.libraries.search;

import com.skachko.shop.order.service.libraries.search.api.ISearchPredicate;
import com.skachko.shop.order.service.libraries.search.api.EPredicateOperator;
import com.skachko.shop.order.service.libraries.search.api.ISearchExpression;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class SearchPredicate implements ISearchPredicate {

    private EPredicateOperator conditionOperator;
    private Collection<ISearchExpression> searchExpressions;
    private Collection<ISearchPredicate> predicates;



}
