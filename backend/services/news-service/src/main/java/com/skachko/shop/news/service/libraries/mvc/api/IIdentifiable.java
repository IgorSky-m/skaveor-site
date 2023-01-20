package com.skachko.shop.news.service.libraries.mvc.api;

public interface IIdentifiable<ID> {

    String ID = "id";

    ID getId();
    void setId(ID id);

}


