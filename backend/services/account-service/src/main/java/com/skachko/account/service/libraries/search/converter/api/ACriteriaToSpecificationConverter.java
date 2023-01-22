package com.skachko.account.service.libraries.search.converter.api;

import com.google.common.base.CaseFormat;
import com.skachko.account.service.libraries.search.api.*;
import com.skachko.account.service.libraries.mvc.api.AEntity;
import com.skachko.account.service.libraries.search.SearchExpressionValidator;
import com.skachko.shop.account.service.libraries.search.api.*;
import com.skachko.account.service.libraries.search.converter.Column;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;


//TODO REFACTOR
//TODO EXCEPTIONS
/**
 *  universal criteria param resolver from front to backend
 * @param <T> class
 */
public abstract class ACriteriaToSpecificationConverter<T> implements ICriteriaToSpecificationConverter<T> {

    private final CriteriaBuilderOperationsFactory bf;

    private final EntityParameters<T> entityParameters;

    private final ISearchExpressionValidator validator;

    public ACriteriaToSpecificationConverter(ISearchExpressionValidator validator) {
        this.validator = validator;
        this.entityParameters = initEntityParameters(getGenericClass());
        this.bf = CriteriaBuilderOperationsFactory.getDefault();

    }

    private ACriteriaToSpecificationConverter(Class<T> entityClass, ISearchExpressionValidator validator) {
        this.entityParameters = initEntityParameters(entityClass);
        this.validator = validator;
        this.bf = CriteriaBuilderOperationsFactory.getDefault();
    }

    private ACriteriaToSpecificationConverter(Class<T> entityClass, ISearchExpressionValidator validator, CriteriaBuilderOperationsFactory bf) {
        this.entityParameters = initEntityParameters(entityClass);
        this.validator = validator;
        this.bf = bf;
    }

    @Override
    public Specification<T> convert(ISearchCriteria criteria) {
        return (root, query, builder) -> build(criteria, root, query, builder);
    }

    private Predicate build(ISearchCriteria criteria, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return isNullOrEmpty(criteria.getSearchPredicate()) ?
                builder.and() : //always true
                build(criteria.getSearchPredicate(), root, query, builder);
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

        String finalField;
        Path path = null;
        Map<String, Column> resolveColumns = entityParameters.columns;
        Column column = null;
        for (String intermediateField : expression.getField().split("\\.")) {
            try {

                finalField = intermediateField;
                column = resolveColumns.getOrDefault(finalField, null);

                if (column == null) {
                    throw new IllegalArgumentException();
                }

                path = path != null ? path.get(column.getField()) : root.get(column.getField());

                if (EColumnType.REF.equals(column.getColumnType())) {
                    resolveColumns = column.getSubColumns();
                }

            } catch (Exception e) {
                throw new IllegalArgumentException("can't found field " + intermediateField);
            }
        }
        if (path == null) {
            throw new IllegalArgumentException("path can't be null");
        }

        return bf.get(column.getColumnType())
                .build(builder, path, column , expression);


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
        return new EntityParameters<>(persistentClass, initColumns(persistentClass, 1));
    }


    private Map<String, Column> initColumns(Class<?> persistentClass, int currentLevel){
        Map<String, Column> columns = new HashMap<>();
        for (Field field : persistentClass.getDeclaredFields()) {

            if (field.isAnnotationPresent(Transient.class) || Modifier.isStatic(field.getModifiers())){
                continue;
            }

            Class<?> type = field.getType();

            if (Collection.class.isAssignableFrom(field.getType())) {
                ParameterizedType collectionType = (ParameterizedType) field.getGenericType();
                type = (Class<?>) collectionType.getActualTypeArguments()[0];
            }

            Map<String, Column> subColumns = Collections.EMPTY_MAP;
            EColumnType columnType = EColumnType.getColumnTypeByClass(type);
            int MAX_DEEP_CRITERIA_PARAMS_LEVEL = 3;
            if (EColumnType.REF.equals(columnType) && currentLevel <= MAX_DEEP_CRITERIA_PARAMS_LEVEL) {
                subColumns = initColumns(type, ++currentLevel);
            }

            String snakeCaseFieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
            columns.put(snakeCaseFieldName, new Column(field.getName(), type, columnType, subColumns));

        }

        if (!persistentClass.equals(AEntity.class)) {
            columns.putAll(initColumns(persistentClass.getSuperclass(), currentLevel));
        }

        return columns;

    }


    protected static final class EntityParameters<T> {
        private final Class<T> persistentClass;
        private final Map<String, Column> columns;

        public EntityParameters(Class<T> persistentClass, Map<String, Column> columns) {
            this.persistentClass = persistentClass;
            this.columns = new HashMap<>(columns);
        }

        private void addColumns(Map<String, Column> columns) {
            this.columns.putAll(columns);
        }

        private Column getColumn(String field) {
            return columns.getOrDefault(field, null);
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