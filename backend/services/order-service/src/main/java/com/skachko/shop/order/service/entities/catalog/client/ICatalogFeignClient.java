package com.skachko.shop.order.service.entities.catalog.client;

import com.skachko.shop.order.service.entities.catalog.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "CATALOG-SERVICE", url = "http://localhost:9000")
public interface ICatalogFeignClient {

    @GetMapping("/items/{id}/exists")
    ResponseEntity<Boolean> isExist(@PathVariable UUID id);

    @GetMapping("/items/{id}")
    Item getOne(@PathVariable UUID id);

    @GetMapping("/items")
    List<Item> getAll(@RequestParam String criteria);
}
