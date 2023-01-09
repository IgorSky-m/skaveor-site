package com.skachko.libraries.mvc.api;

import com.skachko.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.libraries.mvc.exceptions.ServiceException;
import com.skachko.libraries.search.api.ICriteriaToSpecificationConverter;
import com.skachko.libraries.search.api.ISearchCriteria;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public abstract class AProjectionCRUDService<PROJECTION extends IIdentifiable<ID>, CLS extends PROJECTION, ID>
       extends AProjectionReadService<PROJECTION, CLS, ID> implements ICRUDService<PROJECTION, ID> {

    public AProjectionCRUDService(
            JpaRepositoryImplementation<CLS, ID> delegate,
            Function<PROJECTION, CLS> toClassFunc,
            ICriteriaToSpecificationConverter<CLS> converter,
            MessageSource messageSource
    ) {
        super(delegate, toClassFunc, converter, messageSource);
    }



    @Transactional
    @Override
    public PROJECTION save(PROJECTION entity) {
        try {
            return getRepository().save(toClass(entity));
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.create.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public List<PROJECTION> save(Collection<PROJECTION> entities) {
        try {
            List<CLS> saved = getRepository().saveAll(convertToCollectionClass(entities));

            if (entities.size() != saved.size()) {
                throw new IllegalArgumentException(getMessageSource().getMessage("error.crud.create.list", null, LocaleContextHolder.getLocale()));
            }

            return convertToCollectionProjection(saved);

        } catch (ServiceException e){
            throw e;
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.create.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public PROJECTION update(ID id, PROJECTION projection) {
        try {

            CLS oneById = getRepository().findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            projection.setId(oneById.getId());

            return save(projection);

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.update.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }


    @Transactional
    @Override
    public List<PROJECTION> deleteAllById(Collection<ID> ids) {
        List<CLS> founded = getRepository().findAllById(ids);

        if (ids.size() != founded.size()) {

            throw new IllegalArgumentException(
                    getMessageSource()
                            .getMessage(
                                    "error.cud.delete.illegal.found.size",
                                    new Object[]{ids.size(), founded.size()},
                                    LocaleContextHolder.getLocale()
                            )
            );
        }

        getRepository().deleteAllById(ids);

        return convertToCollectionProjection(founded);
    }

    @Transactional
    @Override
    public long delete(ISearchCriteria criteria) {
        try {
            return getRepository().delete(getConverter().convert(criteria));
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.delete.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public PROJECTION deleteById(ID id) {
        try {
            CLS read = getRepository().findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            getRepository().deleteById(id);

            return read;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.delete.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void delete(PROJECTION entity) {
        try {
            getRepository().delete(toClass(entity));
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.delete.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void deleteAllById(Iterable<? extends ID> ids) {
        try {
            getRepository().deleteAllById(ids);
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.delete.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void deleteAll(Iterable<PROJECTION> entities) {
        try {
            getRepository().deleteAll(StreamSupport.stream(entities.spliterator(), false).map(getToClassFunc()).toList());
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.delete.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
        try {
            getRepository().deleteAll();
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.delete.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }




}
