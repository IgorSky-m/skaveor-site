package com.skachko.libraries.search;

import com.skachko.libraries.search.api.EPredicateOperator;
import com.skachko.libraries.search.api.ISearchExpression;
import com.skachko.libraries.search.api.ISearchPredicate;
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