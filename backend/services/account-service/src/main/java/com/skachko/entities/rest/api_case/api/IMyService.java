package com.skachko.entities.rest.api_case.api;

import com.skachko.libraries.search.api.ISearchCriteria;
import com.skachko.entities.rest.api_case.MyEntity;

import java.util.List;
import java.util.UUID;

public interface IMyService {

    MyEntity findById(UUID id);

    List<MyEntity> findAll();

    MyEntity create(MyEntity entity);


    MyEntity update(UUID id, MyEntity entity);

    void delete(UUID id);


    List<MyEntity> get(ISearchCriteria criteria);


}
