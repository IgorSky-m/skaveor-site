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

public abstract class ABaseCRUDService<T extends IBaseEntity<ID>, ID> extends ABaseReadService<T, ID> implements ICRUDService<T, ID> {

    private final IValidator<T, ID> validator;

    public ABaseCRUDService(JpaRepositoryImplementation<T, ID> repository, ICriteriaToSpecificationConverter<T> converter, MessageSource messageSource) {
        super(repository, converter, messageSource);
        validator = new AValidator<T, ID>(messageSource) {};
    }

    public ABaseCRUDService(
            JpaRepositoryImplementation<T, ID> repository,
            ICriteriaToSpecificationConverter<T> converter,
            MessageSource messageSource,
            IValidator<T, ID> validator
    ) {
        super(repository, converter, messageSource);
        this.validator = validator;
    }

    @Transactional
    @Override
    public T save(T t) {
        try {
            validator.validateBeforeCreate(t);
            return getRepository().save(t);
        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.create.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public List<T> save(Collection<T> list) {
       try {
           validator.validateGroupBeforeCreate(list);
            return getRepository().saveAll(list);
       } catch (EntityNotFoundException | ValidationException e) {
           throw e;
       } catch (Exception e){
           getLogger().error(getMessageSource().getMessage("error.crud.create.list", null, LocaleContextHolder.getLocale()));
           throw new ServiceException(e);
       }
    }

    @Transactional
    @Override
    public T update(ID id, Date version, T t) {
        try {
            T old = getById(id);

           validator.validateBeforeUpdate(id, version, old, t);

            t.setId(old.getId());
            t.setDtCreate(old.getDtCreate());
            t.setDtUpdate(new Date());

            return getRepository().save(t);
        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.update.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public T delete(ID id, Date version) {
        try {
            T read = getById(id);

            validator.validateBeforeDelete(id, version, read);

            getRepository().deleteById(id);
            return read;
        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e){
        getLogger().error(getMessageSource().getMessage("error.crud.delete.list", null, LocaleContextHolder.getLocale()));
        throw new ServiceException(e);
        }
    }

    @Transactional
    public List<T> deleteAllById(Collection<ID> ids) {
        List<T> founded = getRepository().findAllById(ids);

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

        return founded;
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
    public void deleteAll() {

        try {
            getRepository().deleteAll();
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.delete.all", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }
}
