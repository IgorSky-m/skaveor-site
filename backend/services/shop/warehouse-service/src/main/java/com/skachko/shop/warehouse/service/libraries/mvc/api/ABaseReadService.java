package com.skachko.shop.warehouse.service.libraries.mvc.api;

import com.skachko.shop.warehouse.service.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.shop.warehouse.service.libraries.mvc.exceptions.ServiceException;
import com.skachko.shop.warehouse.service.libraries.search.api.ICriteriaSortExtractor;
import com.skachko.shop.warehouse.service.libraries.search.api.ICriteriaToSpecificationConverter;
import com.skachko.shop.warehouse.service.libraries.search.api.ISearchCriteria;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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
    private final ICriteriaSortExtractor criteriaSortExtractor;

    public ABaseReadService(JpaRepositoryImplementation<T, ID> repository,
                            ICriteriaToSpecificationConverter<T> converter,
                            MessageSource messageSource,
                            ICriteriaSortExtractor criteriaSortExtractor
    ) {
        init(repository, messageSource);
        this.repository = repository;
        this.messageSource = messageSource;
        this.converter = converter;
        this.criteriaSortExtractor = criteriaSortExtractor;
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

           initializeTableViewFields(t);
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
                initializeTableViewFields(e);
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
        return this.findOne(converter.convert(criteria));
    }


    public Optional<T> findOne(Specification<T> specification) {
        try {
            return repository.findOne(specification)
                    .map(e -> {
                        initializeTableViewFields(e);
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
        return this.findAll(converter.convert(criteria), getSort(criteria));
    }


    public List<T> findAll(Specification<T> specification, Sort sort) {
        try {
            List<T> all = repository.findAll(specification, sort);
            all.forEach(this::initializeTableViewFields);
            return all;
        } catch (Exception e){
            String msg = messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public Page<T> findAll(ISearchCriteria criteria, int page, int size) {
        return this.findAll(converter.convert(criteria), PageRequest.of(page, size, getSort(criteria)));
    }

    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        try {
            return repository.findAll(specification, pageable).map(e -> {
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

    /**
     * Use for initialize lazy fields in table view
     * @param t param
     */
    protected void initializeTableViewFields(T t){}

    /**
     * * Use for initialize lazy (Hibernate.initialize() for example) fields in detailed view
     * @param t param
     */
    protected void initializeDetailedViewFields(T t){}


    protected Sort getSort(ISearchCriteria searchCriteria) {
        return criteriaSortExtractor.getSort(searchCriteria)
                .orElse(Sort.unsorted());
    }
}
