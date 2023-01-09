package com.skachko.libraries.mvc.api;

import com.skachko.libraries.mvc.exceptions.ValidationException;

import java.util.Collection;
import java.util.Date;

public interface IValidator<T extends IBaseEntity<ID>, ID > {

    void validateBeforeCreate(T t) throws ValidationException;
    void validateGroupBeforeCreate(Collection<T> t) throws ValidationException;

    void validateBeforeUpdate(ID id, Date version, T oldEntity, T newEntity) throws ValidationException;

    void validateBeforeDelete(ID id, Date version, T old);

}
