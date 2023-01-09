package com.skachko.news.service.libraries.search.api;

import com.skachko.news.service.libraries.search.SearchExpressionValidator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.ParameterizedType;
import java.util.*;


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

        if (isNullOrEmpty(criteria.getSearchPredicate())) {
            //always true
            return builder.and();
        }

        return build(criteria.getSearchPredicate(), root, query, builder);
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

        return switch (comparisonOperator) {
            case EQUAL -> builder.equal(root.get(field), values[0]);
            case NOT_EQUAL -> builder.notEqual(root.get(field), values[0]);
            case IN -> root.get(field).in(values);
            case NOT_IN -> builder.not(root.get(field).in(values));
            case BETWEEN -> between(builder, field, root, values[0], values[1]);
            case LESS -> lessThan(builder, field, root, values[0]);
            case GREATER -> greaterThan(builder, field, root, values[0]);
            case LESS_OR_EQUAL -> lessOrEqualTo(builder, field, root, values[0]);
            case GREATER_OR_EQUAL -> greaterOrEqualTo(builder, field, root, values[0]);
            case IS_NULL -> builder.isNull(root.get(field));
            case IS_NOT_NULL -> builder.isNotNull(root.get(field));
        };
    }

    private Predicate lessThan(CriteriaBuilder builder, String field, Root<T> root, Object objValue) {

        Column column = entityParameters.getColumn(field);
        String value = (String) objValue;

        return switch (column.columnType) {
            case BYTE -> builder.lessThan(root.get(field), Byte.valueOf(value));
            case SHORT -> builder.lessThan(root.get(field), Short.valueOf(value));
            case INTEGER -> builder.lessThan(root.get(field), Integer.valueOf(value));
            case LONG -> builder.lessThan(root.get(field), Long.valueOf(value));
            case FLOAT -> builder.lessThan(root.get(field), Float.valueOf(value));
            case DOUBLE -> builder.lessThan(root.get(field), Double.valueOf(value));
            case CHARACTER -> builder.lessThan(root.get(field), value.charAt(0));
            case BOOLEAN -> builder.lessThan(root.get(field), Boolean.valueOf(value));
            case STRING -> builder.lessThan(root.get(field), value);
            case UUID -> builder.lessThan(root.get(field), UUID.fromString(value));
            default -> null;
        };

    }

    private Predicate greaterThan(CriteriaBuilder builder, String field, Root<T> root, Object objValue) {

        Column column = entityParameters.getColumn(field);
        String value = (String) objValue;

        return switch (column.columnType) {
            case BYTE -> builder.greaterThan(root.get(field), Byte.valueOf(value));
            case SHORT -> builder.greaterThan(root.get(field), Short.valueOf(value));
            case INTEGER -> builder.greaterThan(root.get(field), Integer.valueOf(value));
            case LONG -> builder.greaterThan(root.get(field), Long.valueOf(value));
            case FLOAT -> builder.greaterThan(root.get(field), Float.valueOf(value));
            case DOUBLE -> builder.greaterThan(root.get(field), Double.valueOf(value));
            case CHARACTER -> builder.greaterThan(root.get(field), value.charAt(0));
            case BOOLEAN -> builder.greaterThan(root.get(field), Boolean.valueOf(value));
            case STRING -> builder.greaterThan(root.get(field), value);
            case UUID -> builder.greaterThan(root.get(field), UUID.fromString(value));
            default -> null;
        };

    }

    private Predicate greaterOrEqualTo(CriteriaBuilder builder, String field, Root<T> root, Object objValue) {

        Column column = entityParameters.getColumn(field);
        String value = (String) objValue;

        return switch (column.columnType) {
            case BYTE -> builder.greaterThanOrEqualTo(root.get(field), Byte.valueOf(value));
            case SHORT -> builder.greaterThanOrEqualTo(root.get(field), Short.valueOf(value));
            case INTEGER -> builder.greaterThanOrEqualTo(root.get(field), Integer.valueOf(value));
            case LONG -> builder.greaterThanOrEqualTo(root.get(field), Long.valueOf(value));
            case FLOAT -> builder.greaterThanOrEqualTo(root.get(field), Float.valueOf(value));
            case DOUBLE -> builder.greaterThanOrEqualTo(root.get(field), Double.valueOf(value));
            case CHARACTER -> builder.greaterThanOrEqualTo(root.get(field), value.charAt(0));
            case BOOLEAN -> builder.greaterThanOrEqualTo(root.get(field), Boolean.valueOf(value));
            case STRING -> builder.greaterThanOrEqualTo(root.get(field), value);
            case UUID -> builder.greaterThanOrEqualTo(root.get(field), UUID.fromString(value));
            default -> null;
        };


    }

    private Predicate lessOrEqualTo(CriteriaBuilder builder, String field, Root<T> root, Object objValue) {

        Column column = entityParameters.getColumn(field);
        String value = (String) objValue;

        return switch (column.columnType) {
            case BYTE -> builder.lessThanOrEqualTo(root.get(field), Byte.valueOf(value));
            case SHORT -> builder.lessThanOrEqualTo(root.get(field), Short.valueOf(value));
            case INTEGER -> builder.lessThanOrEqualTo(root.get(field), Integer.valueOf(value));
            case LONG -> builder.lessThanOrEqualTo(root.get(field), Long.valueOf(value));
            case FLOAT -> builder.lessThanOrEqualTo(root.get(field), Float.valueOf(value));
            case DOUBLE -> builder.lessThanOrEqualTo(root.get(field), Double.valueOf(value));
            case CHARACTER -> builder.lessThanOrEqualTo(root.get(field), value.charAt(0));
            case BOOLEAN -> builder.lessThanOrEqualTo(root.get(field), Boolean.valueOf(value));
            case STRING -> builder.lessThanOrEqualTo(root.get(field), value);
            case UUID -> builder.lessThanOrEqualTo(root.get(field), UUID.fromString(value));
            default -> null;
        };


    }

    private Predicate between(CriteriaBuilder builder, String field, Root<T> root, Object objValue1, Object objValue2) {

        Column column = entityParameters.getColumn(field);
        String value1 = (String) objValue1;
        String value2 = (String) objValue2;

        return switch (column.columnType) {
            case BYTE -> builder.between(root.get(field), Byte.valueOf(value1), Byte.valueOf(value2));
            case SHORT -> builder.between(root.get(field), Short.valueOf(value1), Short.valueOf(value2));
            case INTEGER -> builder.between(root.get(field), Integer.valueOf(value1), Integer.valueOf(value2));
            case LONG -> builder.between(root.get(field), Long.valueOf(value1), Long.valueOf(value2));
            case FLOAT -> builder.between(root.get(field), Float.valueOf(value1), Float.valueOf(value2));
            case DOUBLE -> builder.between(root.get(field), Double.valueOf(value1), Double.valueOf(value2));
            case CHARACTER -> builder.between(root.get(field), value1.charAt(0), value2.charAt(0));
            case BOOLEAN -> builder.between(root.get(field), Boolean.valueOf(value1), Boolean.valueOf(value2));
            case STRING -> builder.between(root.get(field), value1, value2);
            case UUID -> builder.between(root.get(field), UUID.fromString(value1), UUID.fromString(value2));
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

        List<Column> columns = Arrays.stream(persistentClass.getDeclaredFields())
                .map(e -> new Column(e.getName(), e.getType(), EColumnType.getColumnTypeByClass(e.getType())))
                .toList();

        return new EntityParameters<>(persistentClass, columns);
    }

    protected static final class EntityParameters<T> {
        private Class<T> persistentClass;
        private List<Column> columns;

        public EntityParameters(Class<T> persistentClass, List<Column> columns) {
            this.persistentClass = persistentClass;
            this.columns = columns;
        }

        private Column getColumn(String field) {
            return columns.stream()
                    .filter(e -> e.field.equals(field))
                    .findFirst()
                    .orElse(null);
        }
    }

    protected static final class Column {
        private String field;
        private Class<?> type;
        private EColumnType columnType;

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
