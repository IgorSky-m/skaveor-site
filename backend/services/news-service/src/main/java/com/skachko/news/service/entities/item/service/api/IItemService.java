package com.skachko.news.service.entities.item.service.api;

import com.skachko.news.service.entities.item.dto.Item;
import com.skachko.news.service.libraries.mvc.api.ICRUDService;

import java.util.UUID;

public interface IItemService extends ICRUDService<Item, UUID> {
}
