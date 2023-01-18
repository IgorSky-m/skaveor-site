package com.skachko.shop.order.service.entities.order.controller;

import com.skachko.shop.order.service.entities.order.dto.OrderRequest;
import com.skachko.shop.order.service.entities.order.dto.OrderResponse;
import com.skachko.shop.order.service.entities.order.dto.PlacedOrderResponse;
import com.skachko.shop.order.service.entities.order.service.api.IOrderHandleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@Log4j2
@RequiredArgsConstructor
public class OrderController {

    private final IOrderHandleService orderService;



    @PostMapping("/place")
    public PlacedOrderResponse placeOrder(@RequestBody OrderRequest request, @RequestHeader String id) {
        return orderService.placeOrder(request);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }
}
