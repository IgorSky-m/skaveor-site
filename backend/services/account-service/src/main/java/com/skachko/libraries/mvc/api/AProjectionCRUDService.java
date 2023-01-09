package com.skachko.libraries.mvc.api;

import com.skachko.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.libraries.mvc.exceptions.ServiceException;
import com.skachko.libraries.mvc.exceptions.ValidationException;
import com.skachko.libraries.search.api.ICriteriaToSpecificationConverter;
import com.skachko.libraries.search.api.ISearchCriteria;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public abstract class AProjectionCRUDService<PROJECTION extends IBaseEntity<ID>, CLS extends PROJECTION, ID>
       extends AProjectionReadService<PROJECTION, CLS, ID> implements ICRUDService<PROJECTION, ID> {

    private final IValidator<PROJECTION, ID> validator;

    public AProjectionCRUDService(
            JpaRepositoryImplementation<CLS, ID> delegate,
            Function<PROJECTION, CLS> toClassFunc,
            ICriteriaToSpecificationConverter<CLS> converter,
            MessageSource messageSource
    ) {
        super(delegate, toClassFunc, converter, messageSource);
        validator = new AValidator<PROJECTION, ID>(messageSource) {};
    }

    public AProjectionCRUDService(
            JpaRepositoryImplementation<CLS, ID> delegate,
            Function<PROJECTION, CLS> toClassFunc,
            ICriteriaToSpecificationConverter<CLS> converter,
            MessageSource messageSource,
            IValidator<PROJECTION, ID> validator
    ) {
        super(delegate, toClassFunc, converter, messageSource);
        this.validator = validator;
    }



    @Transactional
    @Override
    public PROJECTION save(PROJECTION entity) {
        try {

            validator.validateBeforeCreate(entity);
            entity.setDtCreate(new Date());
            entity.setDtUpdate(entity.getDtCreate());

            return getRepository().save(toClass(entity));
        } catch (EntityNotFoundException | ValidationException e) {
            getLogger().error(getMessageSource().getMessage("error.crud.create.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public List<PROJECTION> save(Collection<PROJECTION> entities) {
        try {
            final Date date = new Date();

            validator.validateGroupBeforeCreate(entities);

            List<CLS> saved = getRepository().saveAll(convertToCollectionClass(entities, e -> {
                e.setDtCreate(date);
                e.setDtUpdate(date);
                return e;
            }));

            if (entities.size() != saved.size()) {
                throw new IllegalArgumentException(getMessageSource().getMessage("error.crud.create.list", null, LocaleContextHolder.getLocale()));
            }

            return convertToCollectionProjection(saved);

        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.create.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public PROJECTION update(ID id, Date version, PROJECTION projection) {
        try {

            CLS oneById = getRepository().findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            validator.validateBeforeUpdate(id, version, oneById, projection);

            projection.setId(oneById.getId());
            projection.setDtCreate(oneById.getDtCreate());
            projection.setDtUpdate(new Date());

            return save(projection);

        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.update.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }


    @Transactional
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
    public PROJECTION delete(ID id, Date version) {
        try {
            CLS read = getRepository().findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            validator.validateBeforeDelete(id, version, read);

            getRepository().deleteById(id);

            return read;
        } catch (EntityNotFoundException | ValidationException e) {
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
    public void deleteAll() {
        try {
            getRepository().deleteAll();
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.delete.list", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }








}
