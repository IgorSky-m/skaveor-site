package com.skachko.account.service.support.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StringToDate implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        if(s == null)
            return null;
        try{
            return new Date(Long.parseLong(s));
        } catch (Throwable t){
            throw new IllegalArgumentException(t);
        }
    }
}
