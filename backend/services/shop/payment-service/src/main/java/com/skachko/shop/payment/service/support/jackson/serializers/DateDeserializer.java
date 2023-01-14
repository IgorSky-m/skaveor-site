package com.skachko.shop.payment.service.support.jackson.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * Date from long deserializer
 */
public class DateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String value = p.getText();
        return value == null ? null : new Date(Long.parseLong(value));
    }
}
