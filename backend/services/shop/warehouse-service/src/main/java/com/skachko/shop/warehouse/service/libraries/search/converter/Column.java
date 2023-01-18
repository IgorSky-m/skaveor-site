package com.skachko.shop.warehouse.service.libraries.search.converter;

import com.skachko.shop.warehouse.service.libraries.search.api.EColumnType;

import java.util.Map;

public class Column {

    private Class<?> type;
    private final String field;

    private final EColumnType columnType;

    private final Map<String, Column> subColumns;


    public Column(String field, Class<?> type, EColumnType columnType, Map<String, Column> subColumns) {
        this.field = field;
        this.type = type;
        this.columnType = columnType;
        this.subColumns = subColumns;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public EColumnType getColumnType() {
        return columnType;
    }

    public Map<String, Column> getSubColumns() {
        return subColumns;
    }
}
