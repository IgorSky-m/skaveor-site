package com.skachko.libraries.mvc.api;

import com.skachko.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.libraries.mvc.exceptions.ServiceException;
import com.skachko.libraries.search.api.ICriteriaToSpecificationConverter;
import com.skachko.libraries.search.api.ISearchCriteria;
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
    public T getById(ID id) {
       try {
           return repository.findById(id)
                   .orElseThrow(EntityNotFoundException::new);
       } catch (EntityNotFoundException e){
           throw e;
       } catch (Exception e){
           getLogger().error(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
           throw new ServiceException(e);
       }
    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            return repository.findById(id);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }



    @Override
    public List<T> findAll(Example<T> filter) {
        try {
            return repository.findAll(filter);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public List<T> findAll(Example<T> filter, Sort sort) {
        try {
            return repository.findAll(filter, sort);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.page", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<T> findAll(Example<T> example, Pageable pageable) {
        try {
            return repository.findAll(example, pageable);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.page", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }


    @Override
    public Optional<T> findOne(ISearchCriteria criteria) {
        try {
            return repository.findOne(converter.convert(criteria));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public List<T> findAll(ISearchCriteria criteria) {
        try {
            return repository.findAll(converter.convert(criteria));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<T> findAll(ISearchCriteria criteria, Pageable pageable) {
        try {
            return repository.findAll(converter.convert(criteria), pageable);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.page", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public List<T> findAll(ISearchCriteria criteria, Sort sort) {
        try {
            return repository.findAll(converter.convert(criteria), sort);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public long count(ISearchCriteria criteria) {
        try {
            return repository.count(converter.convert(criteria));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.count", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean exists(ISearchCriteria criteria) {
        try {
            return repository.exists(converter.convert(criteria));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.exist", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
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

}
