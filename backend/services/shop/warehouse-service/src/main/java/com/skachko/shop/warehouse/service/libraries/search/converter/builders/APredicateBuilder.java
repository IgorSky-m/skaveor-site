package com.skachko.shop.warehouse.service.libraries.search.converter.builders;

import com.skachko.shop.warehouse.service.libraries.search.api.EComparisonOperator;
import com.skachko.shop.warehouse.service.libraries.search.api.ISearchExpression;
import com.skachko.shop.warehouse.service.libraries.search.converter.Column;
import com.skachko.shop.warehouse.service.libraries.search.converter.api.IPredicateBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.util.Arrays;
import java.util.function.BiFunction;

public abstract class APredicateBuilder<T extends Comparable<? super T>> implements IPredicateBuilder<T> {

    private final BiFunction<Object, Class<?>, T> convertFunc;

    public APredicateBuilder(BiFunction<Object, Class<?>, T> convertFunc){
        this.convertFunc = convertFunc;
    }

    @Override
    public Predicate build(CriteriaBuilder builder, Path<T> path, Column column, ISearchExpression expression) {

        EComparisonOperator comparisonOperator = expression.getComparisonOperator();
        Object[] values = expression.getValues();
        return switch (comparisonOperator) {
            case BETWEEN -> builder.between(path, convertFunc.apply(values[0], column.getType()), convertFunc.apply(values[1], column.getType()));
            case EQUAL -> builder.equal(path, convertFunc.apply(values[0], column.getType()));
            case NOT_EQUAL -> builder.notEqual(path, convertFunc.apply(values[0], column.getType()));
            case IN -> path.in(Arrays.stream(values).map(e -> convertFunc.apply(e, column.getType())).toArray());
            case NOT_IN -> builder.not(path.in(Arrays.stream(values).map(e -> convertFunc.apply(e, column.getType())).toArray()));
            case GREATER -> builder.greaterThan(path, convertFunc.apply(values[0], column.getType()));
            case LESS -> builder.lessThan(path, convertFunc.apply(values[0], column.getType()));
            case GREATER_OR_EQUAL -> builder.greaterThanOrEqualTo(path, convertFunc.apply(values[0], column.getType()));
            case LESS_OR_EQUAL -> builder.lessThanOrEqualTo(path, convertFunc.apply(values[0], column.getType()));
            case IS_NULL -> builder.isNull(path);
            case IS_NOT_NULL -> builder.isNotNull(path);
        };
    }
}

