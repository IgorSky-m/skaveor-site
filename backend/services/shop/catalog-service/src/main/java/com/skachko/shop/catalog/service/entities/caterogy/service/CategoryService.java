package com.skachko.shop.catalog.service.entities.caterogy.service;

import com.skachko.shop.catalog.service.entities.caterogy.dto.Category;
import com.skachko.shop.catalog.service.entities.caterogy.repository.ICategoryRepository;
import com.skachko.shop.catalog.service.entities.caterogy.service.api.ICategoryService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ABaseCRUDService;
import com.skachko.shop.catalog.service.libraries.mvc.api.AEntity;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.ServiceException;
import com.skachko.shop.catalog.service.libraries.search.api.ACriteriaToSpecificationConverter;
import com.skachko.shop.catalog.service.libraries.search.api.ICriteriaSortExtractor;
import com.skachko.shop.catalog.service.libraries.search.api.ISearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

//TODO refactor error messages to struct messages
@Service
@Slf4j
public class CategoryService extends ABaseCRUDService<Category, UUID> implements ICategoryService {

    private static final String PARENT_CATEGORY_JOIN_FIELD = "parent_category_id";

    public CategoryService(ICategoryRepository repository, MessageSource messageSource, ICriteriaSortExtractor extractor) {
        super(repository, ACriteriaToSpecificationConverter.of(Category.class), messageSource, extractor);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Transactional
    @Override
    public Category save(Category category, Date dtCreate) {

        try {
           if (isParentInvalid(category)) {
               throw new ServiceException(getMessageSource()
                       .getMessage("parent.reference.not.found", null, LocaleContextHolder.getLocale()));
           }
        } catch (EntityNotFoundException | ServiceException e) {
            throw e;
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.create.one", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }

        return super.save(category, dtCreate);
    }

    //TODO refactor to struct validation message
    @Transactional
    @Override
    public List<Category> save(Collection<Category> list, Date dtCreate) {
        int i = 0;
        Set<Integer> invalidParentIndexes = new HashSet<>();
        try {
            for (Category category : list) {
                if (isParentInvalid(category)) {
                    invalidParentIndexes.add(i);
                }
                i ++;
            }

            if (!invalidParentIndexes.isEmpty()) {
                throw new ServiceException(getMessageSource()
                        .getMessage(
                                "parent.reference.not.found",
                                new Object[] {invalidParentIndexes.stream()
                                        .map(Object::toString)
                                        .collect(Collectors.joining(", "))},
                                LocaleContextHolder.getLocale()
                        )
                );
            }
        } catch (EntityNotFoundException | ServiceException e) {
            throw e;
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.create.one", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }

        return super.save(list, dtCreate);
    }

    @Transactional
    @Override
    public Category update(UUID uuid, Date version, Category category, Date dtUpdate) {
        try {
            if (isParentInvalid(category)) {
                throw new ServiceException(getMessageSource()
                        .getMessage("parent.reference.not.found", null, LocaleContextHolder.getLocale()));
            }
        } catch (EntityNotFoundException | ServiceException e) {
            throw e;
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.update.one", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
        return super.update(uuid, version, category, dtUpdate);
    }

    @Override
    public Optional<Category> findOne(ISearchCriteria criteria) {
        Optional<Category> optionalCategory = super.findOne(criteria);

        if (optionalCategory.isPresent()) {
                optionalCategory.map(e -> {
                    e.setSubCategories(findSubCategories(e.getId()));
                    return e;
                });
        }
        return optionalCategory;
    }

    @Transactional(readOnly = true)
    @Override
    public Category getById(UUID uuid) {
        Category category = super.getById(uuid);
        category.setSubCategories(findSubCategories(uuid));
        return category;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> findById(UUID uuid) {
        Optional<Category> optionalCategory = super.findById(uuid);

        if (optionalCategory.isPresent()) {
            optionalCategory.map(e -> {
                e.setSubCategories(findSubCategories(e.getId()));
                return e;
            });
        }
        return optionalCategory;
    }

    private boolean isParentInvalid(Category category) throws EntityNotFoundException {
        if (category.getParentCategory() != null && category.getParentCategory().getId() != null) {
            return getRepository().findById(category.getParentCategory().getId())
                    .isEmpty();
        }
        return false;
    }

    private List<Category> findSubCategories(UUID id) {
        return getRepository()
                .findAll((root, query, builder) ->
                        builder.equal(root.get("parentCategory").get(AEntity.ID), id)
                );
    }
}
