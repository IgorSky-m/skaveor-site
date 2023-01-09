package com.skachko.shop.catalog.service.libraries.search.api;

import java.util.function.Predicate;

public enum EComparisonOperator {
    EQUAL(AVAILABLE_OPERATORS_AMOUNT.SINGLE),
    NOT_EQUAL(AVAILABLE_OPERATORS_AMOUNT.SINGLE),
    IN(AVAILABLE_OPERATORS_AMOUNT.MULTIPLE),
    NOT_IN(AVAILABLE_OPERATORS_AMOUNT.MULTIPLE),
    BETWEEN(AVAILABLE_OPERATORS_AMOUNT.TWO),
    LESS(AVAILABLE_OPERATORS_AMOUNT.SINGLE),
    GREATER(AVAILABLE_OPERATORS_AMOUNT.SINGLE),
    LESS_OR_EQUAL(AVAILABLE_OPERATORS_AMOUNT.SINGLE),
    GREATER_OR_EQUAL(AVAILABLE_OPERATORS_AMOUNT.SINGLE),
    IS_NULL(AVAILABLE_OPERATORS_AMOUNT.NONE),
    IS_NOT_NULL(AVAILABLE_OPERATORS_AMOUNT.NONE);

    public final AVAILABLE_OPERATORS_AMOUNT AMOUNT;


    EComparisonOperator(AVAILABLE_OPERATORS_AMOUNT AMOUNT) {
        this.AMOUNT = AMOUNT;
    }


    public enum AVAILABLE_OPERATORS_AMOUNT {
        SINGLE(e -> e == 1),
        TWO(e -> e == 2),
        NONE(e -> e == 0),
        MULTIPLE(e -> e > 0);

        private final Predicate<Integer> PREDICATE;

        AVAILABLE_OPERATORS_AMOUNT(Predicate<Integer> PREDICATE) {
            this.PREDICATE = PREDICATE;
        }


        public boolean check(int i){
            return PREDICATE.test(i);
        }
    }

}