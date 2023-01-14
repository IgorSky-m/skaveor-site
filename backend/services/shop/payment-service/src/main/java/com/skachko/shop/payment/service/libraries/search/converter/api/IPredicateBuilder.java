package com.skachko.shop.payment.service.libraries.search.converter.api;

import com.skachko.shop.payment.service.libraries.search.api.ISearchExpression;
import com.skachko.shop.payment.service.libraries.search.converter.Column;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

@FunctionalInterface
public interface IPredicateBuilder<T> {

    Predicate build(CriteriaBuilder builder, Path<T> path, Column column, ISearchExpression expression);




}
