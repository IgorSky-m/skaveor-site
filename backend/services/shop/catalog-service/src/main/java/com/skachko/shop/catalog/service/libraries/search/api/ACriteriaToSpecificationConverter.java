package com.skachko.shop.catalog.service.libraries.search.api;

import com.google.common.base.CaseFormat;
import com.skachko.shop.catalog.service.libraries.mvc.api.AEntity;
import com.skachko.shop.catalog.service.libraries.search.SearchExpressionValidator;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;


//TODO REWRITE
public abstract class ACriteriaToSpecificationConverter<T> implements ICriteriaToSpecificationConverter<T> {

    private final EntityParameters<T> entityParameters;

    private final ISearchExpressionValidator validator;

    public ACriteriaToSpecificationConverter(ISearchExpressionValidator validator) {
        this.validator = validator;
        this.entityParameters = initEntityParameters(getGenericClass());
    }

    private ACriteriaToSpecificationConverter(Class<T> entityClass, ISearchExpressionValidator validator) {
        this.validator = validator;
        this.entityParameters = initEntityParameters(entityClass);
    }

    @Override
    public Specification<T> convert(ISearchCriteria criteria) {
        return (root, query, builder) -> build(criteria, root, query, builder);
    }

    private Predicate build(ISearchCriteria criteria, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate predicate = null;
        if (isNullOrEmpty(criteria.getSearchPredicate())) {
            //always true
            predicate = builder.and();
        } else {
            predicate = build(criteria.getSearchPredicate(), root, query, builder);
        }
        return predicate;
    }

    private Predicate build(ISearchPredicate predicate, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (isNullOrEmpty(predicate)) {
            return null;
        }

        if (predicate.getConditionOperator() == null) {
            throw new IllegalArgumentException("logical operator is null");
        }

        final EPredicateOperator conditionOperator = predicate.getConditionOperator();

        Collection<ISearchPredicate> predicates = predicate.getPredicates();

        if (predicates == null) {
            predicates = Collections.EMPTY_LIST;
        }

        List<Predicate> resultPredicates = new java.util.ArrayList<>(predicates.stream()
                .map(e -> build(e, root, query, builder))
                .filter(Objects::nonNull)
                .toList());

        Collection<ISearchExpression> searchExpressions = predicate.getSearchExpressions();

        if (searchExpressions == null || searchExpressions.isEmpty()) {
            searchExpressions = Collections.EMPTY_LIST;
        }

        resultPredicates.addAll(
                searchExpressions.stream()
                        .map(expr -> build(expr, root, query, builder))
                        .filter(Objects::nonNull)
                        .toList()
        );

        return mergePredicates(conditionOperator, resultPredicates, builder);
    }

    private Predicate build(ISearchExpression expression, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        validator.accept(expression);

        final EComparisonOperator comparisonOperator = expression.getComparisonOperator();
        Object[] values = expression.getValues();
        String field = expression.getField();
        Path<Object> path = null;

        for (String s : expression.getField().split("\\.")) {
            String lowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s);
            path = path != null ? path.get(lowerCamel) : root.get(lowerCamel);
            field = lowerCamel;
        }
        if (path == null) {
            throw new IllegalArgumentException("path can't be null");
        }
        return switch (comparisonOperator) {
            case EQUAL -> equal(field, builder, path, values[0]);
            case NOT_EQUAL -> notEqual(field, builder, path, values[0]);
            case IN -> in(field, path, values);
            case NOT_IN -> builder.not(in(field, path, values));
            case BETWEEN -> between(builder, field, path, values[0], values[1]);
            case LESS -> lessThan(builder, field, path, values[0]);
            case GREATER -> greaterThan(builder, field, path, values[0]);
            case LESS_OR_EQUAL -> lessOrEqualTo(builder, field, path, values[0]);
            case GREATER_OR_EQUAL -> greaterOrEqualTo(builder, field, path, values[0]);
            case IS_NULL -> builder.isNull(path);
            case IS_NOT_NULL -> builder.isNotNull(path);
        };
    }

    private Predicate in(String field, Path<Object> path, Object[] values) {
        EColumnType columnType = entityParameters.getColumn(field).columnType;
        return path.in(Arrays.stream(values).map(e -> convert(columnType, e)).toArray());
    }

    private Predicate equal(String field, CriteriaBuilder builder, Path<Object> path, Object value) {
        EColumnType columnType = entityParameters.getColumn(field).columnType;
        return builder.equal(path, convert(columnType, value));
    }

    private Predicate notEqual(String field, CriteriaBuilder builder, Path<Object> path, Object value) {
        EColumnType columnType = entityParameters.getColumn(field).columnType;
        return builder.notEqual(path, convert(columnType, value));
    }

    private Predicate lessThan(CriteriaBuilder builder, String field, Path root, Object objValue) {

        Column column = entityParameters.getColumn(field);
        String value = (String) objValue;
        return switch (column.columnType) {
            case BYTE -> builder.lessThan(root, (Byte)column.columnType.getConvert().apply(value));
            case SHORT -> builder.lessThan(root, (Short) column.columnType.getConvert().apply(value));
            case INTEGER -> builder.lessThan(root,(Integer) column.columnType.getConvert().apply(value));
            case LONG -> builder.lessThan(root, (Long) column.columnType.getConvert().apply(value));
            case FLOAT -> builder.lessThan(root, (Float) column.columnType.getConvert().apply(value));
            case DOUBLE -> builder.lessThan(root, (Double) column.columnType.getConvert().apply(value));
            case CHARACTER -> builder.lessThan(root, (Character) column.columnType.getConvert().apply(value));
            case BOOLEAN -> builder.lessThan(root, (Boolean) column.columnType.getConvert().apply(value));
            case STRING -> builder.lessThan(root, value);
            case UUID -> builder.lessThan(root, (UUID) column.columnType.getConvert().apply(value));
            case DATE -> builder.lessThan(root, (Date) column.columnType.getConvert().apply(value));
            default -> null;
        };

    }

    private T convert (EColumnType columnType, Object value) {
        if (columnType.predicate().test(value.getClass())) {
            return (T) value;
        }
        return (T) columnType.getConvert().apply((String)value);
    }

    private Predicate greaterThan(CriteriaBuilder builder, String field, Path root, Object objValue) {

        Column column = entityParameters.getColumn(field);
        String value = (String) objValue;

        return switch (column.columnType) {
            case BYTE -> builder.greaterThan(root, (Byte)column.columnType.getConvert().apply(value));
            case SHORT -> builder.greaterThan(root, (Short) column.columnType.getConvert().apply(value));
            case INTEGER -> builder.greaterThan(root,(Integer) column.columnType.getConvert().apply(value));
            case LONG -> builder.greaterThan(root, (Long) column.columnType.getConvert().apply(value));
            case FLOAT -> builder.greaterThan(root, (Float) column.columnType.getConvert().apply(value));
            case DOUBLE -> builder.greaterThan(root, (Double) column.columnType.getConvert().apply(value));
            case CHARACTER -> builder.greaterThan(root, (Character) column.columnType.getConvert().apply(value));
            case BOOLEAN -> builder.greaterThan(root, (Boolean) column.columnType.getConvert().apply(value));
            case STRING -> builder.greaterThan(root, value);
            case UUID -> builder.greaterThan(root, (UUID) column.columnType.getConvert().apply(value));
            case DATE -> builder.greaterThan(root, (Date) column.columnType.getConvert().apply(value));
            default -> null;
        };

    }

    private Predicate greaterOrEqualTo(CriteriaBuilder builder, String field, Path root, Object objValue) {

        Column column = entityParameters.getColumn(field);
        String value = (String) objValue;

        return switch (column.columnType) {
            case BYTE -> builder.greaterThanOrEqualTo(root, (Byte)column.columnType.getConvert().apply(value));
            case SHORT -> builder.greaterThanOrEqualTo(root, (Short) column.columnType.getConvert().apply(value));
            case INTEGER -> builder.greaterThanOrEqualTo(root,(Integer) column.columnType.getConvert().apply(value));
            case LONG -> builder.greaterThanOrEqualTo(root, (Long) column.columnType.getConvert().apply(value));
            case FLOAT -> builder.greaterThanOrEqualTo(root, (Float) column.columnType.getConvert().apply(value));
            case DOUBLE -> builder.greaterThanOrEqualTo(root, (Double) column.columnType.getConvert().apply(value));
            case CHARACTER -> builder.greaterThanOrEqualTo(root, (Character) column.columnType.getConvert().apply(value));
            case BOOLEAN -> builder.greaterThanOrEqualTo(root, (Boolean) column.columnType.getConvert().apply(value));
            case STRING -> builder.greaterThanOrEqualTo(root, value);
            case UUID -> builder.greaterThanOrEqualTo(root, (UUID) column.columnType.getConvert().apply(value));
            case DATE -> builder.greaterThanOrEqualTo(root, (Date) column.columnType.getConvert().apply(value));
            default -> null;
        };
    }

    private Predicate lessOrEqualTo(CriteriaBuilder builder, String field, Path root, Object objValue) {

        Column column = entityParameters.getColumn(field);
        String value = (String) objValue;

        return switch (column.columnType) {
            case BYTE -> builder.lessThanOrEqualTo(root, (Byte)column.columnType.getConvert().apply(value));
            case SHORT -> builder.lessThanOrEqualTo(root, (Short) column.columnType.getConvert().apply(value));
            case INTEGER -> builder.lessThanOrEqualTo(root,(Integer) column.columnType.getConvert().apply(value));
            case LONG -> builder.lessThanOrEqualTo(root, (Long) column.columnType.getConvert().apply(value));
            case FLOAT -> builder.lessThanOrEqualTo(root, (Float) column.columnType.getConvert().apply(value));
            case DOUBLE -> builder.lessThanOrEqualTo(root, (Double) column.columnType.getConvert().apply(value));
            case CHARACTER -> builder.lessThanOrEqualTo(root, (Character) column.columnType.getConvert().apply(value));
            case BOOLEAN -> builder.lessThanOrEqualTo(root, (Boolean) column.columnType.getConvert().apply(value));
            case STRING -> builder.lessThanOrEqualTo(root, value);
            case UUID -> builder.lessThanOrEqualTo(root, (UUID) column.columnType.getConvert().apply(value));
            case DATE -> builder.lessThanOrEqualTo(root, (Date) column.columnType.getConvert().apply(value));
            default -> null;
        };


    }

    private Predicate between(CriteriaBuilder builder, String field, Path root, Object objValue1, Object objValue2) {

        Column column = entityParameters.getColumn(field);
        String value1 = (String) objValue1;
        String value2 = (String) objValue2;

        return switch (column.columnType) {
            case BYTE -> builder.between(root, Byte.valueOf(value1), Byte.valueOf(value2));
            case SHORT -> builder.between(root, Short.valueOf(value1), Short.valueOf(value2));
            case INTEGER -> builder.between(root, Integer.valueOf(value1), Integer.valueOf(value2));
            case LONG -> builder.between(root, Long.valueOf(value1), Long.valueOf(value2));
            case FLOAT -> builder.between(root, Float.valueOf(value1), Float.valueOf(value2));
            case DOUBLE -> builder.between(root, Double.valueOf(value1), Double.valueOf(value2));
            case CHARACTER -> builder.between(root, value1.charAt(0), value2.charAt(0));
            case BOOLEAN -> builder.between(root, Boolean.valueOf(value1), Boolean.valueOf(value2));
            case STRING -> builder.between(root, value1, value2);
            case UUID -> builder.between(root, UUID.fromString(value1), UUID.fromString(value2));
            case DATE -> builder.between(root, (Date) column.columnType.getConvert().apply(value1), (Date) column.columnType.getConvert().apply(value2));
            default -> null;
        };

    }

    private boolean isNullOrEmpty(ISearchPredicate predicate) {
        return predicate == null || (predicate.getPredicates() == null || predicate.getPredicates().isEmpty()) &&
                (predicate.getSearchExpressions() == null || predicate.getSearchExpressions().isEmpty());
    }

    private Predicate mergePredicates(EPredicateOperator conditionOperator, Collection<Predicate> predicates, CriteriaBuilder builder) {
        Predicate[] predicatesArray = predicates.toArray(Predicate[]::new);
        return switch (conditionOperator) {
            case OR -> builder.or(predicatesArray);
            case AND -> builder.and(predicatesArray);
        };
    }

    private Class<T> getGenericClass(){
        return (Class<T>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }


    private EntityParameters<T> initEntityParameters(Class<T> persistentClass) {
        return new EntityParameters<>(persistentClass, getColumns(persistentClass));
    }


    private List<Column> getColumns(Class<?> persistentClass){
        List<Column> columns = Arrays.stream(persistentClass.getDeclaredFields())
                .map(e -> new Column(e.getName(), e.getType(), EColumnType.getColumnTypeByClass(e.getType())))
                .collect(Collectors.toCollection(ArrayList::new));

        if (!persistentClass.equals(AEntity.class)) {
            columns.addAll(getColumns(persistentClass.getSuperclass()));
        }

        return columns;

    }


    protected static final class EntityParameters<T> {
        private final Class<T> persistentClass;
        private final List<Column> columns;

        public EntityParameters(Class<T> persistentClass, List<Column> columns) {
            this.persistentClass = persistentClass;
            this.columns = new ArrayList<>(columns);
        }

        private void addColumns(List<Column> columns) {
            this.columns.addAll(columns);
        }

        private Column getColumn(String field) {
            return columns.stream()
                    .filter(e -> e.field.equals(field))
                    .findFirst()
                    .orElse(null);
        }
    }

    protected static final class Column {

        private Class<?> type;
        private final String field;

        private final EColumnType columnType;

        public Column(String field, Class<?> type, EColumnType columnType) {
            this.field = field;
            this.type = type;
            this.columnType = columnType;
        }
    }


    public static <T> ICriteriaToSpecificationConverter<T> of(Class<T> tClass){
        return new DefaultConverter<>(tClass);
    }

    public static <T> ICriteriaToSpecificationConverter<T> of(Class<T> tClass, ISearchExpressionValidator validator){
        return new DefaultConverter<>(tClass, validator);
    }

    private static final class DefaultConverter<T> extends ACriteriaToSpecificationConverter<T> {

        private DefaultConverter(Class<T> clazz) {
            super(clazz, new SearchExpressionValidator());
        }

        private DefaultConverter(Class<T> clazz, ISearchExpressionValidator validator) {
            super(clazz, validator);
        }
    }


}
