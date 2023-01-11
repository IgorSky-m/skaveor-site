package com.skachko.shop.catalog.service.entities.caterogy.controller;

import com.skachko.shop.catalog.service.entities.caterogy.dto.Category;
import com.skachko.shop.catalog.service.entities.caterogy.service.api.ICategoryService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ARestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController extends ARestController<Category, UUID> {

    public CategoryController(ICategoryService service) {
        super(service);
    }
}
