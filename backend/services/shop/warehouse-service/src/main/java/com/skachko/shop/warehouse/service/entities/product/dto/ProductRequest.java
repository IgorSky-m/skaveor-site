package com.skachko.shop.warehouse.service.entities.product.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    private String name;
    private long price;
    private long quantity;
}
