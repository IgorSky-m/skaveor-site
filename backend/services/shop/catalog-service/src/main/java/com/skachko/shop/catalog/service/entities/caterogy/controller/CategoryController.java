package com.skachko.shop.catalog.service.entities.caterogy.controller;

import com.skachko.shop.catalog.service.entities.caterogy.dto.Category;
import com.skachko.shop.catalog.service.entities.caterogy.service.api.ICategoryService;
import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.entities.item.service.api.IItemService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ARestController;
import com.skachko.shop.catalog.service.libraries.search.annotations.ASearchCriteria;
import com.skachko.shop.catalog.service.libraries.search.api.ISearchCriteria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController extends ARestController<Category, UUID> {

    private final IItemService itemService;

    public CategoryController(ICategoryService service, IItemService itemService) {
        super(service);
        this.itemService = itemService;
    }

    @GetMapping("/{id}/items")
    public List<Item> getItemsFromCategory(@PathVariable UUID id, @ASearchCriteria ISearchCriteria criteria) {
        return this.itemService.findAllByCategory(criteria, id);
    }
}
