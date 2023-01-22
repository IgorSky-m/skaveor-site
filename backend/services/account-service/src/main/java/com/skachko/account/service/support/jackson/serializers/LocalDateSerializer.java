package com.skachko.account.service.support.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate from long serializer
 */
public class LocalDateSerializer extends StdSerializer<LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateSerializer(String localDateFormatterPattern){
        super(LocalDate.class);
        formatter = DateTimeFormatter.ofPattern(localDateFormatterPattern);
    }


    @Override
    public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeObject(value == null ? null : formatter.format(value));
    }
}
