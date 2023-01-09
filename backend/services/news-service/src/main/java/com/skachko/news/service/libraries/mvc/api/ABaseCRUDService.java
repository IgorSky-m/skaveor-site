package com.skachko.news.service.libraries.mvc.api;

import com.skachko.news.service.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.news.service.libraries.mvc.exceptions.ServiceException;
import com.skachko.news.service.libraries.mvc.exceptions.ValidationException;
import com.skachko.news.service.libraries.search.api.ICriteriaToSpecificationConverter;
import com.skachko.news.service.libraries.search.api.ISearchCriteria;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class ABaseCRUDService<T extends AEntity<ID>, ID> extends ABaseReadService<T, ID> implements ICRUDService<T, ID> {

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

            if (t.getDtCreate() == null) {
                t.setDtCreate(new Date());
            }

            t.setDtUpdate(t.getDtCreate());

            return getRepository().save(t);

        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.create.one", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Transactional
    @Override
    public List<T> save(Collection<T> list) {
       try {

           final Date dtCreate = new Date();

           list.forEach(e -> {
               if (e.getDtCreate() == null) {
                   e.setDtCreate(dtCreate);
               }
               e.setDtUpdate(e.getDtCreate());
           });

            return getRepository().saveAll(list);

       } catch (EntityNotFoundException | ValidationException e) {
           throw e;
       } catch (Exception e){
           String msg = getMessageSource().getMessage("error.crud.create.list", null, LocaleContextHolder.getLocale());
           getLogger().error(msg);
           throw new ServiceException(msg);
       }
    }

    @Transactional
    public T update(ID id, Date version, T t) {
        try {
            T old = getById(id);

            if (old.getDtUpdate().getTime() != version.getTime()){
                throw new EntityNotFoundException();
            }

            t.setId(old.getId());
            t.setDtCreate(old.getDtCreate());

            if (t.getDtUpdate() == null) {
                t.setDtUpdate(new Date());
            }

            t.setDtDelete(old.getDtDelete());
            t.setMeta(old.getMeta());

            return getRepository().save(t);

        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.update.one", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Transactional
    @Override
    public T update(IPathParamContainer<ID> paramContainer, Date version, T t) {
        return update(paramContainer.getParam(ID), version, t);
    }

    @Transactional
    public T delete(ID id, Date version) {
        try {
            T read = getById(id);

            if (read.getDtUpdate().getTime() != version.getTime()){
                throw new EntityNotFoundException();
            }

            getRepository().deleteById(id);

            return read;

        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.delete.one", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public T delete(IPathParamContainer<ID> paramContainer, Date version) {
        return delete(paramContainer.getParam(ID), version);
    }

    @Transactional
    public List<T> deleteAllById(Collection<ID> ids) {
        try {
            List<T> founded = getRepository().findAllById(ids);

            if (ids.size() != founded.size()) {

                throw new ServiceException(
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
        } catch (ServiceException e){
            throw e;
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.delete.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }

    }

    @Transactional
    public long delete(ISearchCriteria criteria) {

        try {
            return getRepository().delete(getConverter().convert(criteria));
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.delete.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Transactional
    public void deleteAll() {
        try {
            getRepository().deleteAll();
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.delete.all", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }
}
