package com.skachko.shop.catalog.service.entities.characteristics.service.api;

import com.skachko.shop.catalog.service.entities.characteristics.dto.Characteristics;
import com.skachko.shop.catalog.service.libraries.mvc.api.ICRUDService;

import java.util.UUID;

public interface ICharacteristicsService extends ICRUDService<Characteristics, UUID> {
}
