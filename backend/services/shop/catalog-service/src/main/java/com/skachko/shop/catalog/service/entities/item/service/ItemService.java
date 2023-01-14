package com.skachko.shop.catalog.service.entities.item.service;

import com.skachko.shop.catalog.service.entities.caterogy.dto.Category;
import com.skachko.shop.catalog.service.entities.caterogy.service.api.ICategoryService;
import com.skachko.shop.catalog.service.entities.characteristics.dto.Characteristics;
import com.skachko.shop.catalog.service.entities.characteristics.service.api.ICharacteristicsService;
import com.skachko.shop.catalog.service.entities.deal.api.EDealType;
import com.skachko.shop.catalog.service.entities.deal.dto.Deal;
import com.skachko.shop.catalog.service.entities.deal.service.api.IDealService;
import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.entities.item.service.api.IItemService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ABaseCRUDService;
import com.skachko.shop.catalog.service.libraries.mvc.api.AEntity;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.ServiceException;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.ValidationException;
import com.skachko.shop.catalog.service.libraries.search.SearchCriteria;
import com.skachko.shop.catalog.service.libraries.search.SearchPredicate;
import com.skachko.shop.catalog.service.libraries.search.api.*;
import com.skachko.shop.catalog.service.libraries.search.converter.api.ACriteriaToSpecificationConverter;
import com.skachko.shop.catalog.service.support.utils.IsEmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemService extends ABaseCRUDService<Item, UUID> implements IItemService {

    private final ICharacteristicsService characteristicsService;
    private final IDealService dealService;

    private final ICategoryService categoryService;

    public ItemService(
            JpaRepositoryImplementation<Item, UUID> delegate,
            MessageSource messageSource,
            ICharacteristicsService characteristicsService,
            IDealService dealService,
            ICriteriaSortExtractor extractor,
            ICategoryService categoryService
    ) {
        super(delegate, ACriteriaToSpecificationConverter.of(Item.class), messageSource, extractor);
        this.characteristicsService = characteristicsService;
        this.dealService = dealService;
        this.categoryService = categoryService;
    }

    @Transactional
    @Override
    public Item save(Item item, Date dtCreate) {
        if (item.getCharacteristics() != null) {
            item.getCharacteristics().setDtCreate(dtCreate);
            characteristicsService.save(item.getCharacteristics());
        }

        if (item.getDeals() != null && !item.getDeals().isEmpty()) {
            item.getDeals().forEach(e -> e.setDtCreate(dtCreate));
            dealService.save(item.getDeals());
        }

        checkCategoriesNotEmpty(item.getCategories());
        checkCategoriesValid(item.getCategories());

        return super.save(item, dtCreate);
    }


    @Transactional
    @Override
    public List<Item> save(Collection<Item> list, Date dtCreate) {
        if (list == null || list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<Characteristics> characteristics = new ArrayList<>();
        List<Deal> deals = new ArrayList<>();
        list.forEach(e -> {
            e.setDtCreate(dtCreate);
            e.setDtUpdate(dtCreate);

            if (e.getCharacteristics() != null) {
                e.getCharacteristics().setDtCreate(dtCreate);
                characteristics.add(e.getCharacteristics());
            }

            if (IsEmptyUtil.isNotNullOrEmpty(e.getDeals())) {
                deals.forEach(d -> d.setDtCreate(dtCreate));
                deals.addAll(e.getDeals());
            }

            checkCategoriesNotEmpty(e.getCategories());
            checkCategoriesValid(e.getCategories());

        });

        characteristicsService.save(characteristics);
        dealService.save(deals);


        try {
            return getRepository().saveAll(list);
        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e) {
            String msg = getMessageSource().getMessage("error.crud.create.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    @Override
    public List<Item> findAll(ISearchCriteria criteria) {
        return super.findAll(criteria);
    }

    //TODO UPDATE \ DELETE

    @Override
    public List<Item> findAllByCategory(ISearchCriteria criteria, UUID category) {
        Specification<Item> convert = getConverter()
                .convert(criteria)
                .and((r, c, b) -> b.equal(r.get("categories").get(AEntity.ID), category));

        return super.findAll(convert, getSort(criteria));
    }

    @Transactional
    @Override
    public Page<Item> findPageByCategory(ISearchCriteria criteria, UUID category, int page, int size) {
        Specification<Item> convert = getConverter()
                .convert(criteria)
                .and((r, c, b) -> b.equal(r.get("categories").get(AEntity.ID), category));
        return super.findAll(convert, PageRequest.of(page, size, getSort(criteria)));
    }

    @Override
    public List<Item> findAllByDealType(EDealType type, ISearchCriteria criteria) {
        Specification<Item> dealsSpec = getConverter()
                .convert(criteria)
                .and((r, c, b) -> b.equal(r.get("deals").get("type"), type));

        return findAll(dealsSpec, getSort(criteria));
    }

    @Override
    public Page<Item> findAllByDealType(EDealType type, ISearchCriteria criteria, int page, int size) {
        Specification<Item> dealsSpec = getConverter()
                .convert(criteria)
                .and((r, c, b) -> b.equal(r.get("deals").get("type"), type));

        return findAll(dealsSpec, PageRequest.of(page, size, getSort(criteria)));
    }



    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    protected void initializeDetailedViewFields(Item item) {
        Hibernate.initialize(item.getCharacteristics());
        Hibernate.initialize(item.getPictures());
        Hibernate.initialize(item.getDeals());
    }

    @Override
    protected void initializeTableViewFields(Item item){
        Hibernate.initialize(item.getDeals());
    }

    //TODO change exceptions to readable on front
    private void checkCategoriesNotEmpty(List<Category> categories) throws ServiceException {
        if (categories == null || categories.isEmpty()) {
            throw new ServiceException(getMessageSource()
                    .getMessage("item.categories.empty",
                            null,
                            LocaleContextHolder.getLocale())
            );
        }
    }


    //TODO change exceptions to readable on front
    private void checkCategoriesValid(List<Category> categories) throws ServiceException {
        Set<UUID> categoryIdentifiers = categories.stream()
                .map(AEntity::getId)
                .collect(Collectors.toCollection(HashSet::new));

        SearchCriteria cr = new SearchCriteria();
        SearchPredicate pr = new SearchPredicate();
        pr.setConditionOperator(EPredicateOperator.AND);
        ISearchExpression exr = ISearchExpression.of(
                AEntity.ID,
                EComparisonOperator.IN,
                categoryIdentifiers.toArray()
        );
        pr.setSearchExpressions(new ArrayList<>() {{
            add(exr);
        }});
        cr.setSearchPredicate(pr);

        Set<UUID> existedCategories = categoryService.findAll(cr)
                .stream()
                .map(AEntity::getId)
                .collect(Collectors.toCollection(HashSet::new));

        if (!categoryIdentifiers.removeAll(existedCategories) || !categoryIdentifiers.isEmpty()) {
            throw new ServiceException(getMessageSource().getMessage(
                    "item.categories.not.exist",
                    new Object[]{
                            categoryIdentifiers.stream()
                                    .map(UUID::toString)
                                    .collect(Collectors.joining(", "))
                    },
                    LocaleContextHolder.getLocale()
            ));
        }
    }


}
