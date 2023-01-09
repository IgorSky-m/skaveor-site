package com.skachko.shop.catalog.service.entities.deal.service.api;

import com.skachko.shop.catalog.service.entities.deal.dto.Deal;
import com.skachko.shop.catalog.service.libraries.mvc.api.ICRUDService;

import java.util.UUID;

public interface IDealService extends ICRUDService<Deal, UUID> {
}
