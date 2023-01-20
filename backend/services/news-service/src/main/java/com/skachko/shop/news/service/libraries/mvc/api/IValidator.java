package com.skachko.shop.news.service.libraries.mvc.api;

import com.skachko.shop.news.service.libraries.mvc.exceptions.ValidationException;

import java.util.Collection;
import java.util.Date;

public interface IValidator<T extends AEntity, ID > {

    void validateCreate(T t) throws ValidationException;
    void validateGroupBeforeCreate(Collection<T> t) throws ValidationException;

    void validateBeforeUpdate(ID id, Date version, T oldEntity, T newEntity) throws ValidationException;

    void validateBeforeDelete(ID id, Date version, T old);

}
