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

public abstract class ABaseCRUDService<T extends IIdentifiable<ID>, ID> extends ABaseReadService<T, ID> implements ICRUDService<T, ID> {

    public ABaseCRUDService(JpaRepositoryImplementation<T, ID> repository, ICriteriaToSpecificationConverter<T> converter, MessageSource messageSource) {
        super(repository, converter, messageSource);
    }

    @Transactional
    @Override
    public T save(T t) {
        try {
            return getRepository().save(t);
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.create.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public List<T> save(Collection<T> list) {
       try {
            return getRepository().saveAll(list);
       } catch (Exception e){
           getLogger().error(getMessageSource().getMessage("error.crud.create.list", null, LocaleContextHolder.getLocale()));
           throw new ServiceException(e);
       }
    }

    @Transactional
    @Override
    public T update(ID id, T t) {
        try {
            T oneById = getById(id);
            t.setId(oneById.getId());
            return getRepository().save(t);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.update.one", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }

    @Transactional
    @Override
    public T deleteById(ID id) {
        try {
            T read = getById(id);
            getRepository().deleteById(id);
            return read;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e){
        getLogger().error(getMessageSource().getMessage("error.crud.delete.list", null, LocaleContextHolder.getLocale()));
        throw new ServiceException(e);
    }
    }

    @Transactional
    @Override
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
    public void deleteAll() {

        try {
            getRepository().deleteAll();
        } catch (Exception e){
            getLogger().error(getMessageSource().getMessage("error.crud.delete.all", null, LocaleContextHolder.getLocale()));
            throw new ServiceException(e);
        }
    }
}
