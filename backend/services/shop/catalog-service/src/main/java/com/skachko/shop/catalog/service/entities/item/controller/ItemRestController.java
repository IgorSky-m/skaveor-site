package com.skachko.shop.catalog.service.entities.item.controller;

import com.skachko.shop.catalog.service.entities.item.dto.Item;
import com.skachko.shop.catalog.service.entities.item.service.api.IItemService;
import com.skachko.shop.catalog.service.libraries.mvc.api.ARestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemRestController extends ARestController<Item, UUID> {

    private final IItemService itemService;

    public ItemRestController(IItemService service) {
        super(service);
        this.itemService = service;
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> isExist(@PathVariable UUID id) {
        return ResponseEntity.ok(itemService.existsById(id));
    }

    @GetMapping("/search")
    public List<Item> search(@RequestParam(name = "q") String q) {
        return itemService.search(q);
    }
}
