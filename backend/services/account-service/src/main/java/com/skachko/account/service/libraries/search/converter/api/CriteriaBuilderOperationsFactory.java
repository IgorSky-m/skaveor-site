package com.skachko.account.service.libraries.search.converter.api;

import com.skachko.account.service.libraries.search.api.EColumnType;
import com.skachko.account.service.libraries.search.converter.builders.APredicateBuilder;
import com.skachko.account.service.libraries.search.converter.builders.EnumPredicateBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public abstract class CriteriaBuilderOperationsFactory {

    private final Map<EColumnType, IPredicateBuilder<?>> builders;

    private CriteriaBuilderOperationsFactory() {
        this.builders = new HashMap<>();
    }

    public CriteriaBuilderOperationsFactory(Map<EColumnType, IPredicateBuilder<?>> builders) {
        this.builders = new HashMap<>(builders);
    }

    public void add(EColumnType operator, IPredicateBuilder<?> builder) {
        this.builders.put(operator, builder);
    }


    public void addAll(Map<EColumnType, IPredicateBuilder<?>> builders) {
        this.builders.putAll(builders);
    }


    public IPredicateBuilder<?> get(EColumnType columnType) {
        IPredicateBuilder<?> builder = builders.getOrDefault(columnType, null);

        if (builder == null) {
            throw new IllegalArgumentException("can't find builder");
        }

        return builder;
    }


    public IPredicateBuilder<?> getOrDefault(EColumnType columnType, IPredicateBuilder<?> defaultBuilder) {
        return builders.getOrDefault(columnType, defaultBuilder);
    }


    public static CriteriaBuilderOperationsFactory getDefault() {
        return new CriteriaBuilderOperationsFactory(
                new HashMap<>() {{
                    put(EColumnType.BYTE, new APredicateBuilder<>((o, c) -> ((Number) o).byteValue()) {});
                    put(EColumnType.SHORT, new APredicateBuilder<>((o, c) -> ((Number) o).shortValue()) {});
                    put(EColumnType.INTEGER, new APredicateBuilder<>((o, c) -> ((Number) o).intValue()) {});
                    put(EColumnType.LONG, new APredicateBuilder<>((o, c) -> ((Number) o).longValue()) {});
                    put(EColumnType.FLOAT, new APredicateBuilder<>((o, c) -> ((Number) o).floatValue()) {});
                    put(EColumnType.DOUBLE, new APredicateBuilder<>((o, c) -> ((Number) o).doubleValue()) {});
                    put(EColumnType.BIG_DECIMAL, new APredicateBuilder<>((o, c) -> BigDecimal.valueOf(((Number) o).doubleValue())) {});
                    put(EColumnType.ENUM, new EnumPredicateBuilder() {});
                    put(EColumnType.BOOLEAN, new APredicateBuilder<>((o, c) -> (Boolean) o) {});
                    put(EColumnType.CHARACTER, new APredicateBuilder<>((o, c) -> o != null ? ((String) o).charAt(0) : null) {});
                    put(EColumnType.STRING, new APredicateBuilder<>((o, c) -> (String) o) {});
                    put(EColumnType.UUID, new APredicateBuilder<>((o, c) -> {
                        if (o instanceof UUID) {
                            return (UUID) o;
                        } else if (o instanceof String) {
                            return UUID.fromString((String) o);
                        } else throw new IllegalArgumentException();
                    }) {});
                    put(EColumnType.DATE, new APredicateBuilder<>((o, c) -> new Date(((Number)o).longValue())) {});
                }}
        ) {
        };
    }
}
