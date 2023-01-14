package com.skachko.shop.catalog.service.entities.item.service.api;

import com.skachko.shop.catalog.service.entities.deal.api.EDealType;
import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.libraries.mvc.api.ICRUDService;
import com.skachko.shop.catalog.service.libraries.search.api.ISearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IItemService extends ICRUDService<Item, UUID> {

    List<Item> findAllByCategory(ISearchCriteria criteria, UUID category);
    Page<Item> findPageByCategory(ISearchCriteria criteria, UUID category, int page, int size);
    List<Item> findAllByDealType(EDealType type, ISearchCriteria criteria);
    Page<Item> findAllByDealType(EDealType type, ISearchCriteria criteria, int page, int size);
}
