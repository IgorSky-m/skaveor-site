package com.skachko.news.service.support.jackson.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate from long deserializer
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateDeserializer(String localDateFormatterPattern){
        formatter = DateTimeFormatter.ofPattern(localDateFormatterPattern);
    }

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        String value = p.getText();

        return value == null ? null : LocalDate.parse(value, formatter);
    }
}
