package com.skachko.libraries.search.api;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public enum EColumnType {
    BYTE(e -> e != null && (e.isAssignableFrom(byte.class) || e.isAssignableFrom(Byte.class))),
    SHORT(e -> e != null && ( e.isAssignableFrom(short.class) || e.isAssignableFrom(Short.class))),
    INTEGER(e -> e != null && ( e.isAssignableFrom(int.class) || e.isAssignableFrom(Integer.class))),
    LONG(e -> e != null && ( e.isAssignableFrom(long.class) || e.isAssignableFrom(Long.class))),
    FLOAT(e -> e != null && ( e.isAssignableFrom(float.class) || e.isAssignableFrom(Float.class))),
    DOUBLE(e -> e != null && ( e.isAssignableFrom(double.class) || e.isAssignableFrom(Double.class))),
    BOOLEAN(e -> e != null && (e.isAssignableFrom(boolean.class) || e.isAssignableFrom(Boolean.class))),
    CHARACTER(e -> e != null && ( e.isAssignableFrom(char.class) || e.isAssignableFrom(Character.class))),
    STRING(e -> e != null && ( e.isAssignableFrom(String.class))),
    UUID(e -> e != null && (e.isAssignableFrom(java.util.UUID.class))),
    REF(e -> e != null && ( e.isAssignableFrom(Object.class)));

    private final Predicate<Class<?>> predicate;

    private static final Set<EColumnType> excludeRef = getExcludeRefValues();

    EColumnType(Predicate<Class<?>> predicate) {
        this.predicate = predicate;
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
