package com.skachko.shop.catalog.service.entities.item.service;

import com.skachko.shop.catalog.service.entities.characteristics.dto.Characteristics;
import com.skachko.shop.catalog.service.entities.characteristics.service.api.ICharacteristicsService;
import com.skachko.shop.catalog.service.entities.deal.dto.Deal;
import com.skachko.shop.catalog.service.entities.deal.service.api.IDealService;
import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.entities.item.service.api.IItemService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ABaseCRUDService;
import com.skachko.shop.catalog.service.libraries.search.api.ACriteriaToSpecificationConverter;
import com.skachko.shop.catalog.service.support.utils.IsEmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class ItemService extends ABaseCRUDService<Item, UUID> implements IItemService {

    private final ICharacteristicsService characteristicsService;
    private final IDealService dealService;

    public ItemService(
            JpaRepositoryImplementation<Item, UUID> delegate,
            MessageSource messageSource,
            ICharacteristicsService characteristicsService,
            IDealService dealService
    ) {
        super(delegate, ACriteriaToSpecificationConverter.of(Item.class), messageSource);
        this.characteristicsService = characteristicsService;
        this.dealService = dealService;
    }

    @Transactional
    @Override
    public Item save(Item item) {

        if (item.getCharacteristics() == null) {
            characteristicsService.save(item.getCharacteristics());
        }

        if (item.getDeals() != null && !item.getDeals().isEmpty()) {
            dealService.save(item.getDeals());
        }

        return super.save(item);
    }

    @Transactional
    @Override
    public List<Item> save(Collection<Item> list) {
        if (list == null || list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<Characteristics> characteristics = new ArrayList<>();
        List<Deal> deals = new ArrayList<>();
        list.forEach(e -> {
            if (e.getCharacteristics() != null) {
                characteristics.add(e.getCharacteristics());
            }
            if (IsEmptyUtil.isNullOrEmpty(e.getDeals())) {
                deals.addAll(e.getDeals());
            }
        });

        characteristicsService.save(characteristics);
        dealService.save(deals);

        return super.save(list);
    }

    //TODO UPDATE \ DELETE

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
}
