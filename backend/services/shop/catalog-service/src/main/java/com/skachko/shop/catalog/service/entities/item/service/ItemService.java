package com.skachko.shop.catalog.service.entities.item.service;

import com.skachko.shop.catalog.service.entities.characteristics.dto.Characteristics;
import com.skachko.shop.catalog.service.entities.characteristics.service.api.ICharacteristicsService;
import com.skachko.shop.catalog.service.entities.deal.dto.Deal;
import com.skachko.shop.catalog.service.entities.deal.service.api.IDealService;
import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.entities.item.service.api.IItemService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ABaseCRUDService;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.EntityNotFoundException;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.ServiceException;
import com.skachko.shop.catalog.service.libraries.mvc.exceptions.ValidationException;
import com.skachko.shop.catalog.service.libraries.search.api.ACriteriaToSpecificationConverter;
import com.skachko.shop.catalog.service.libraries.search.api.ICriteriaSortExtractor;
import com.skachko.shop.catalog.service.support.utils.IsEmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
            IDealService dealService,
            ICriteriaSortExtractor extractor
    ) {
        super(delegate, ACriteriaToSpecificationConverter.of(Item.class), messageSource, extractor);
        this.characteristicsService = characteristicsService;
        this.dealService = dealService;
    }

    @Transactional
    @Override
    public Item save(Item item) {
        final Date date = new Date();
        if (item.getCharacteristics() != null) {
            item.getCharacteristics().setDtCreate(date);
            characteristicsService.save(item.getCharacteristics());
        }

        if (item.getDeals() != null && !item.getDeals().isEmpty()) {
            item.getDeals().forEach(e -> e.setDtCreate(date));
            dealService.save(item.getDeals());
        }

        return super.save(item);
    }

    @Transactional
    @Override
    public List<Item> save(Collection<Item> list) {
        Date dtCreate = new Date();
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
        });

        characteristicsService.save(characteristics);
        dealService.save(deals);

        try {
            return getRepository().saveAll(list);
        } catch (EntityNotFoundException | ValidationException e) {
            throw e;
        } catch (Exception e){
            String msg = getMessageSource().getMessage("error.crud.create.list", null, LocaleContextHolder.getLocale());
            getLogger().error(msg);
            throw new ServiceException(msg);
        }
    }

    //TODO UPDATE \ DELETE


    @Transactional
    @Override
    public Item update(UUID uuid, Date version, Item item) {
        return super.update(uuid, version, item);
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
}
