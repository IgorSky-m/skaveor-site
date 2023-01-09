package com.skachko.shop.catalog.service.entities.item.service.api;

import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.libraries.mvc.api.ICRUDService;

import java.util.UUID;

public interface IItemService extends ICRUDService<Item, UUID> {
}
