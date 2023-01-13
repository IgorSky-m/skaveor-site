package com.skachko.shop.catalog.service.entities.item.controller;

import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.entities.item.service.api.IItemService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ARestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "*")
public class ItemRestController extends ARestController<Item, UUID> {
    public ItemRestController(IItemService service) {
        super(service);
    }

}
