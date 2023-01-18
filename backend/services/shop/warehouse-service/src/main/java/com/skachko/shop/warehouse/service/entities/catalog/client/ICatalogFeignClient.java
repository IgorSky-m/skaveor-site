package com.skachko.shop.warehouse.service.entities.catalog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8081/product")
public interface ICatalogFeignClient {

    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") UUID id,
            @RequestParam long quantity
    );
}
