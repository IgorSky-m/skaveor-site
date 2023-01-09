package com.skachko.news.service.entities.item.controller;

import com.skachko.news.service.entities.item.dto.Item;
import com.skachko.news.service.entities.item.service.api.IItemService;
import com.skachko.news.service.libraries.mvc.api.ARestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemRestController extends ARestController<Item, UUID> {

    public ItemRestController(IItemService service) {
        super(service);
    }

}
