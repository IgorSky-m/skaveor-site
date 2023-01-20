package com.skachko.shop.news.service.libraries.search.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public enum EColumnType {
    UUID(e -> e != null && (e.equals(java.util.UUID.class)), java.util.UUID::fromString),
    ENUM(e -> e != null && e.isEnum(), e -> e),
    BIG_DECIMAL(e -> e != null && e.equals(BigDecimal.class), BigDecimal::new),
    BYTE(e -> e != null && (e.equals(byte.class) || e.equals(Byte.class)),Byte::valueOf),
    SHORT(e -> e != null && ( e.equals(short.class) || e.equals(Short.class)), Short::valueOf),
    INTEGER(e -> e != null && ( e.equals(int.class) || e.equals(Integer.class)), Integer::valueOf),
    LONG(e -> e != null && ( e.equals(long.class) || e.equals(Long.class)), Long::valueOf),
    FLOAT(e -> e != null && ( e.equals(float.class) || e.equals(Float.class)), Float::valueOf),
    DOUBLE(e -> e != null && ( e.equals(double.class) || e.equals(Double.class)), Double::valueOf),
    BOOLEAN(e -> e != null && (e.equals(boolean.class) || e.equals(Boolean.class)), Boolean::valueOf),
    CHARACTER(e -> e != null && ( e.equals(char.class) || e.equals(Character.class)), s -> s.charAt(0)),
    STRING(e -> e != null && ( e.equals(String.class)), s -> s),
    DATE(e -> e != null && e.equals(Date.class), s -> new Date(Long.parseLong(s))),
    REF(e -> e != null && ( Object.class.isAssignableFrom(e)), s -> s);

    private final Predicate<Class<?>> predicate;

    private final Function<String, Object> convert;

    public Function<String, Object> getConvert() {
        return convert;
    }

    public Predicate<Class<?>> predicate() {
        return predicate;
    }

    private static final Set<EColumnType> excludeRef = getExcludeRefValues();

    EColumnType(Predicate<Class<?>> predicate, Function<String, Object> convert) {
        this.predicate = predicate;
        this.convert = convert;
    }


    public static EColumnType getColumnTypeByClass(Class<?> clazz){

        EColumnType columnType = excludeRef.stream()
                .filter(e -> e.predicate.test(clazz))
                .findFirst()
                .orElse(null);

        if (columnType == null && EColumnType.REF.predicate.test(clazz)) {
            columnType = EColumnType.REF;
        }

        return columnType;
    }

    private static Set<EColumnType> getExcludeRefValues(){

        Set <EColumnType> eColumnTypes = new HashSet<>(Set.of(values()));

        boolean remove = eColumnTypes.remove(EColumnType.REF);

        if (!remove) {
            throw new IllegalArgumentException("can't delete ref");
        }

        return eColumnTypes;
    }
}
