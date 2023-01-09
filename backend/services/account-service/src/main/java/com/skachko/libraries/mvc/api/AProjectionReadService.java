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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public abstract class AProjectionReadService<PROJECTION extends IIdentifiable<ID>, CLS extends PROJECTION, ID>
        implements IReadService<PROJECTION, ID> {

    private final Function<PROJECTION, CLS> toClassFunc;

    private final JpaRepositoryImplementation<CLS, ID> delegate;
    private final MessageSource messageSource;
    private final ICriteriaToSpecificationConverter<CLS> converter;

    public AProjectionReadService(
            JpaRepositoryImplementation<CLS, ID> delegate,
            Function<PROJECTION, CLS> toClassFunc,
            ICriteriaToSpecificationConverter<CLS> converter,
            MessageSource messageSource
    ) {
        this.toClassFunc = toClassFunc;
        this.delegate = delegate;
        this.messageSource = messageSource;
        this.converter = converter;
    }


    @Override
    public PROJECTION getById(ID id) {
        try {
            return findById(id)
                    .orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e){
            throw e;
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public Optional<PROJECTION> findById(ID id) {
        try {
            return delegate.findById(id).map(getToProjectionFunc());
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public boolean existsById(ID id) {
        try {
            return delegate.existsById(id);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.exist", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public List<PROJECTION> findAll() {
        try {
            return convertToCollectionProjection(delegate.findAll());
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public List<PROJECTION> findAllById(Iterable<ID> ids) {
        try {
            return convertToCollectionProjection(delegate.findAllById(ids));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public long count() {
        try {
            return delegate.count();
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.count", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public CLS getReferenceById(ID id) {
        try {
            return delegate.getReferenceById(id);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }


    public List<PROJECTION> findAll(Sort sort) {
        try {
            return convertToCollectionProjection(delegate.findAll(sort));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public List<PROJECTION> findAll(Example<PROJECTION> example) {
        try {
            return convertToCollectionProjection(delegate.findAll(Example.of(toClass(example.getProbe()))));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public List<PROJECTION> findAll(Example<PROJECTION> example, Sort sort) {
        try {
            return convertToCollectionProjection(delegate.findAll(Example.of(toClass(example.getProbe())), sort));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public Page<PROJECTION> findAll(Pageable pageable) {
        try {
            return delegate.findAll(pageable)
                    .map(getToProjectionFunc());
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.page", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public Optional<PROJECTION> findOne(Example<PROJECTION> example) {
        try {
            return delegate.findOne(Example.of(toClass(example.getProbe())))
                    .map(getToProjectionFunc());
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public Page<PROJECTION> findAll(Example<PROJECTION> example, Pageable pageable) {
        try {
            return delegate.findAll(Example.of(toClass(example.getProbe())), pageable)
                    .map(getToProjectionFunc());
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.page", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public long count(Example<PROJECTION> example) {
        try {
            return delegate.count(Example.of(toClass(example.getProbe())));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.count", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    public boolean exists(Example<PROJECTION> example) {
        try {
            return delegate.exists(Example.of(toClass(example.getProbe())));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.exist", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }


    //------------------ criteria start ---------------------

    @Override
    public Optional<PROJECTION> findOne(ISearchCriteria criteria) {
        try {
            return delegate.findOne(converter.convert(criteria))
                    .map(this::toInterface);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PROJECTION> findAll(ISearchCriteria criteria) {
        try {
            return convertToCollectionProjection(delegate.findAll(converter.convert(criteria)));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<PROJECTION> findAll(ISearchCriteria criteria, Pageable pageable) {
        try {
            return delegate.findAll(converter.convert(criteria), pageable)
                    .map(this::toInterface);
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PROJECTION> findAll(ISearchCriteria criteria, Sort sort) {
        try {
            return convertToCollectionProjection(delegate.findAll(converter.convert(criteria), sort));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.read.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public long count(ISearchCriteria criteria) {
        try {
            return delegate.count(converter.convert(criteria));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.count", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean exists(ISearchCriteria criteria) {
        try {
            return delegate.exists(converter.convert(criteria));
        } catch (Exception e){
            getLogger().error(messageSource.getMessage("error.crud.exist", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }
    //----------------- criteria end ------------------------


    protected PROJECTION toInterface(CLS cls) {
        return getToProjectionFunc().apply(cls);
    }

    protected CLS toClass(PROJECTION i) {
        return getToClassFunc().apply(i);
    }

    protected Function<CLS, PROJECTION> getToProjectionFunc() {
        return e -> e;
    }

    protected Function<PROJECTION, CLS> getToClassFunc() {
        return toClassFunc;
    }

    protected List<CLS> convertToCollectionClass(Collection<PROJECTION> interfaces) {
        return convertToCollectionClass(interfaces, Function.identity(), ArrayList::new);
    }

    protected List<CLS> convertToCollectionClass(Collection<PROJECTION> interfaces, Function<CLS, CLS> customMapFunc) {
        return convertToCollectionClass(interfaces, customMapFunc, ArrayList::new);
    }

    protected List<PROJECTION> convertToCollectionProjection(Collection<CLS> entities) {
        return convertToCollectionProjection(entities, Function.identity(), ArrayList::new);
    }

    protected <C extends Collection<CLS>> C convertToCollectionClass(Collection<PROJECTION> interfaces, Function<CLS, CLS> customMapFunc, Supplier<C> collectionSupplier) {
        return interfaces.stream()
                .map(getToClassFunc())
                .map(customMapFunc)
                .collect(Collectors.toCollection(collectionSupplier));
    }

    protected <C extends Collection<PROJECTION>> C convertToCollectionProjection(Collection<CLS> entities, Function<PROJECTION, PROJECTION> customMapFunc, Supplier<C> collectionSupplier) {
        return entities.stream()
                .map(getToProjectionFunc())
                .map(customMapFunc)
                .collect(Collectors.toCollection(collectionSupplier));
    }


    protected MessageSource getMessageSource(){
        return messageSource;
    }

    protected JpaRepositoryImplementation<CLS, ID> getRepository(){
        return delegate;
    }


    protected abstract Logger getLogger();


    private void init(JpaRepositoryImplementation<CLS, ID> repository, MessageSource messageSource){
        if (getLogger() == null || repository == null || messageSource == null) {
            throw new IllegalArgumentException("can't be null");
        }
    }

    protected ICriteriaToSpecificationConverter<CLS> getConverter(){
        return converter;
    }

}
