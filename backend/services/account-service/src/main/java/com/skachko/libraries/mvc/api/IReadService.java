package com.skachko.libraries.mvc.api;

import com.skachko.libraries.search.api.ISearchCriteria;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IReadService<T, ID> {

    T getById(ID id);

    Optional<T> findById(ID id);

    List<T> findAll();

    List<T> findAll(Example<T> filter);

    List<T> findAll(Example<T> filter, Sort sort);

    Page<T> findAll(Pageable pageable);

    Page<T> findAll(Example<T> example, Pageable pageable);

    Optional<T> findOne(ISearchCriteria criteria);

    List<T> findAll(ISearchCriteria criteria);

    Page<T> findAll(ISearchCriteria criteria, Pageable pageable);

    List<T> findAll(ISearchCriteria criteria, Sort sort);

    long count(ISearchCriteria criteria);

    boolean exists(ISearchCriteria criteria);


}
