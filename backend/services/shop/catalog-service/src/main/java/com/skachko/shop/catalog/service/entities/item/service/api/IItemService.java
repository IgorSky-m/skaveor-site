package com.skachko.shop.catalog.service.entities.item.service.api;

import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.libraries.mvc.api.ICRUDService;
import com.skachko.shop.catalog.service.libraries.search.api.ISearchCriteria;

import java.util.List;
import java.util.UUID;

public interface IItemService extends ICRUDService<Item, UUID> {

    List<Item> findAllByCategory(ISearchCriteria criteria, UUID category);
}
