package com.skachko.shop.warehouse.service.libraries.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.skachko.shop.warehouse.service.libraries.filter.annotation.FieldViewLevel;

public class ViewFilter extends SimpleBeanPropertyFilter implements PropertyFilter {

    private final byte methodView;

    public ViewFilter(byte responseViewLevel) {
        this.methodView = responseViewLevel;
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator gen, SerializerProvider prov, PropertyWriter writer) throws Exception {
        FieldViewLevel annotation = writer.getAnnotation(FieldViewLevel.class);

        if (annotation == null) {
            writer.serializeAsField(pojo, gen, prov);
            return;
        }

        if (annotation.isHidden()) {
            return;
        }

        if (isAvailable(annotation.viewLevel(), annotation.modeMatch())) {
            writer.serializeAsField(pojo, gen, prov);
        }
    }


    private boolean isAvailable(byte[] fieldViewLevels, byte modeMatch){

        for (byte fieldView : fieldViewLevels) {

            if (isAvailableToView(methodView, fieldView, modeMatch)){
                return true;
            }
        }

        return false;
    }

    protected boolean isAvailableToView(byte methodView, byte fieldView, byte matchMode){
        return switch (matchMode) {
            case ViewConstraints.MatchMode.HIERARCHICAL ->  fieldView <= methodView;
            case ViewConstraints.MatchMode.STRICT -> fieldView == methodView;
            default -> false;
        };
    }
}
