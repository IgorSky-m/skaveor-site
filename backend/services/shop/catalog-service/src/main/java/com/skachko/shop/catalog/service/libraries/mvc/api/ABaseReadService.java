package com.skachko.shop.catalog.service.libraries.mvc.api;

import com.skachko.shop.catalog.service.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.ServiceException;
import com.skachko.shop.catalog.service.libraries.search.api.ICriteriaToSpecificationConverter;
import com.skachko.shop.catalog.service.libraries.search.api.ISearchCriteria;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public abstract class ABaseReadService<T, ID> implements IReadService<T, ID> {

    protected final String ID = AEntity.ID + IPathParamContainer.BASE_ID_SUFFIX;

    private final JpaRepositoryImplementation<T, ID> repository;
    private final MessageSource messageSource;

    private final ICriteriaToSpecificationConverter<T> converter;


    public ABaseReadService(JpaRepositoryImplementation<T, ID> repository, ICriteriaToSpecificationConverter<T> converter, MessageSource messageSource) {
        init(repository, messageSource);
        this.repository = repository;
        this.messageSource = messageSource;
        this.converter = converter;
    }

    @Override
    public T getById(IPathParamContainer<ID> paramContainer) {
        return getById(paramContainer.getParam(ID));
    }

    @Override
    public Optional<T> findById(IPathParamContainer<ID> paramContainer) {
        return findById(paramContainer.getParam(ID));
    }

    @Override
    public T getById(ID id) {
       try {

           T t = repository.findById(id)
                   .orElseThrow(EntityNotFoundException::new);

           initializeDetailedViewFields(t);

           return t;
       } catch (EntityNotFoundException e){
           throw e;
       } catch (Exception e){
           String msg = messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale());
           getLogger().error(msg);
           throw new ServiceException(msg);
       }
    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            return repository.findById(id).map(e -> {
                initializeDetailedViewFields(e);
                return e;
            });
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            List<T> all = repository.findAll();
            all.forEach(this::initializeTableViewFields);
            return all;
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }



    @Override
    public List<T> findAll(Example<T> filter) {
        try {
            List<T> all = repository.findAll(filter);
            all.forEach(this::initializeTableViewFields);
            return all;
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<T> findAll(Example<T> filter, Sort sort) {
        try {
            List<T> all = repository.findAll(filter, sort);
            all.forEach(this::initializeTableViewFields);
            return all;
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        try {
            return repository.findAll(pageable).map(e -> {
                initializeTableViewFields(e);
                return e;
            });
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.page", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public Page<T> findAll(Example<T> example, Pageable pageable) {
        try {
            return repository.findAll(example, pageable).map(e -> {
                initializeTableViewFields(e);
                return e;
            });
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.page", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }


    @Override
    public Optional<T> findOne(ISearchCriteria criteria) {
        try {
            return repository.findOne(converter.convert(criteria))
                    .map(e -> {
                        initializeDetailedViewFields(e);
                        return e;
                    });
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<T> findAll(ISearchCriteria criteria) {
        try {
            List<T> all = repository.findAll(converter.convert(criteria));
            all.forEach(this::initializeTableViewFields);
            return all;
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public Page<T> findAll(ISearchCriteria criteria, Pageable pageable) {
        try {
            return repository.findAll(converter.convert(criteria), pageable).map(e -> {
                initializeTableViewFields(e);
                return e;
            });
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.page", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<T> findAll(ISearchCriteria criteria, Sort sort) {
        try {
            List<T> all = repository.findAll(converter.convert(criteria), sort);
            all.forEach(this::initializeTableViewFields);
            return all;
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public long count(ISearchCriteria criteria) {
        try {
            return repository.count(converter.convert(criteria));
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.count", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public boolean exists(ISearchCriteria criteria) {
        try {
            return repository.exists(converter.convert(criteria));
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.exist", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }


    protected MessageSource getMessageSource(){
        return messageSource;
    }

    protected JpaRepositoryImplementation<T, ID> getRepository(){
        return repository;
    }

    protected abstract Logger getLogger();


    private void init(JpaRepositoryImplementation<T, ID> repository, MessageSource messageSource){
        if (getLogger() == null || repository == null || messageSource == null) {
            throw new IllegalArgumentException("can't be null");
        }
    }

    protected ICriteriaToSpecificationConverter<T> getConverter(){
        return converter;
    }

    protected void initializeTableViewFields(T t){}
    protected void initializeDetailedViewFields(T t){}
}
