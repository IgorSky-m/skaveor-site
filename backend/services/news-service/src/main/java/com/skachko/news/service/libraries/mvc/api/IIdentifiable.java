package com.skachko.news.service.libraries.mvc.api;

public interface IIdentifiable<ID> {

    String ID = "id";

    ID getId();
    void setId(ID id);

}


