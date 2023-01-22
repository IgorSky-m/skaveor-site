package com.skachko.account.service.libraries.search.converter.builders;

import java.util.function.BiFunction;

public class EnumPredicateBuilder extends APredicateBuilder{

    public EnumPredicateBuilder() {
        super((o ,c) -> Enum.valueOf((Class<? extends Enum>)c, (String) o));
    }

    public EnumPredicateBuilder(BiFunction<Object, Class<?>, Object> convertFunc) {
        super(convertFunc);
    }
}
