package com.skachko.shop.account.service.support.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Date;

/**
 * Date from long serializer
 */
public class DateSerializer extends StdSerializer<Date> {

    public DateSerializer() { super(Date.class); }
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeObject(value == null ? null : value.getTime());
    }
}
