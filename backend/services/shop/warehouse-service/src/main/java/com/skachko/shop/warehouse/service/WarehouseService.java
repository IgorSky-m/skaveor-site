package com.skachko.shop.warehouse.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WarehouseService {
    public static void main(String[] args) {
        SpringApplication.run(WarehouseService.class, args);
    }
}