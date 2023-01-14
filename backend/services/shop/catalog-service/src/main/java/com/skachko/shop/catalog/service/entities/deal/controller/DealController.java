package com.skachko.shop.catalog.service.entities.deal.controller;

import com.skachko.shop.catalog.service.entities.deal.api.EDealType;
import com.skachko.shop.catalog.service.entities.deal.service.api.IDealService;
import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.entities.item.service.ItemService;
import com.skachko.shop.catalog.service.libraries.filter.ViewConstraints;
import com.skachko.shop.catalog.service.libraries.filter.annotation.ResponseViewLevel;
import com.skachko.shop.catalog.service.libraries.search.annotations.ASearchCriteria;
import com.skachko.shop.catalog.service.libraries.search.api.ISearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deals")
@CrossOrigin(origins = "*")
public class DealController {

    private final IDealService service;
    private final ItemService itemService;

    public DealController(
            ItemService itemService,
            IDealService service
    ) {
        this.service = service;
        this.itemService = itemService;
    }

    @GetMapping("/types")
    private List<String> geDealTypes() {
        return service.geDealTypes();
    }

    @ResponseViewLevel(ViewConstraints.ViewLevel.TABLE)
    @GetMapping
    public List<Item> getItemList(
            @ASearchCriteria ISearchCriteria criteria,
            @RequestParam EDealType type
    ) {
        return itemService.findAllByDealType(type, criteria);
    }



    @ResponseViewLevel(ViewConstraints.ViewLevel.TABLE)
    @GetMapping("/page")
    public Page<Item> getItemPage(
            @ASearchCriteria ISearchCriteria criteria,
            @RequestParam EDealType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return itemService.findAllByDealType(type, criteria, page, size);
    }

}
