package com.skachko.entities.rest.api_case.api;

import com.skachko.libraries.mvc.api.IIdentifiable;

import java.util.Date;
import java.util.UUID;

public interface IMyEntity extends IIdentifiable<UUID> {

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    int getNumber();
    void setNumber(int number);

    Date getDate();
    void setDate(Date date);
}
