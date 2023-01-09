package com.skachko.libraries.mvc.api;

import com.skachko.libraries.search.api.ISearchCriteria;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface IJpaProjectionRepository<T, ID> {

    T save(T entity);

    List<T> saveAll(Iterable<T> entities);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    List<T> findAll();

    List<T> findAllById(Iterable<ID> ids);

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<T> entities);

    void deleteAll();

    void flush();

    T saveAndFlush(T entity);

    List<T> saveAllAndFlush(Collection<T> entities);

    void deleteAllInBatch(Collection<T> entities);

    void deleteAllByIdInBatch(Iterable<ID> ids);


    void deleteAllInBatch();

    List<T> findAll(Sort sort);

    List<T> findAll(Example<T> example);

    List<T> findAll(Example<T> example, Sort sort);

    Page<T> findAll(Pageable pageable);

    Optional<T> findOne(Example<T> example);

    Page<T> findAll(Example<T> example, Pageable pageable);

    long count(Example<T> example);

    boolean exists(Example<T> example);

    //----------------- criteria start ------------------------

    Optional<T> findOne(ISearchCriteria criteria);

    List<T> findAll(ISearchCriteria criteria);

    Page<T> findAll(ISearchCriteria criteria, Pageable pageable);

    List<T> findAll(ISearchCriteria criteria, Sort sort);

    long count(ISearchCriteria criteria);

    boolean exists(ISearchCriteria criteria);

    long delete(ISearchCriteria criteria);

    //----------------- criteria end ------------------------

}
