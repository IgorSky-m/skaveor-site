package com.skachko.account.service.libraries.search.converter.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skachko.account.service.libraries.search.api.EColumnType;
import com.skachko.account.service.libraries.search.converter.Column;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class StringToParamValueConverter implements IParamValueConverter {

    public ObjectMapper mapper;

    public StringToParamValueConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T convertAndCast(Object s, Column column) {
        try {
            if (s instanceof String) {
                return (T) convertFromString((String) s, column);
            } else if (s instanceof Number) {
                return (T) convertFromNumber((Number) s, column);
            } else if (s instanceof Boolean) {
                if (EColumnType.BOOLEAN.equals(column.getType())) {
                    return (T) s;
                }
                throw new IllegalArgumentException();
            } else {
                throw new IllegalArgumentException();
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("can't convert and cast value: " + s + " in column: " + column.getField());
        }

    }


    private Object convertFromNumber(Number s, Column column) throws Exception {
        return switch (column.getColumnType()) {
            case FLOAT -> s.floatValue();
            case BYTE -> s.byteValue();
            case DATE -> new Date(s.longValue());
            case LONG -> s.longValue();
            case INTEGER -> s.intValue();
            case BIG_DECIMAL -> BigDecimal.valueOf(s.doubleValue());
            case DOUBLE -> s.doubleValue();
            case SHORT -> s.shortValue();
            default -> throw new IllegalArgumentException("unsupported type");
        };
    }


    public Object convertFromString(String s, Column column) throws Exception {
        return switch (column.getColumnType()) {
            case REF -> mapper.readValue(s, column.getType());
            case ENUM -> Enum.valueOf((Class<? extends Enum>) column.getType(), s);
            case STRING -> s;
            case UUID -> UUID.fromString(s);
            case CHARACTER -> switch (s.length()) {
                case 0 -> null;
                case 1 -> s.charAt(0);
                default -> throw new IllegalArgumentException();
            };
            default -> throw new IllegalArgumentException();
        };
    }

}
