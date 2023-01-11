package com.skachko.shop.catalog.service.entities.caterogy.service.api;

import com.skachko.shop.catalog.service.entities.caterogy.dto.Category;
import com.skachko.shop.catalog.service.libraries.mvc.api.ICRUDService;

import java.util.UUID;

public interface ICategoryService extends ICRUDService<Category, UUID> {
}
