package com.skachko.shop.warehouse.service.libraries.search.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.skachko.shop.warehouse.service.libraries.search.SearchPredicate;

import java.util.Collection;

@JsonDeserialize(as = SearchPredicate.class)
public interface ISearchPredicate {
    EPredicateOperator getConditionOperator();
    Collection<ISearchExpression> getSearchExpressions();
    Collection<ISearchPredicate> getPredicates();
}