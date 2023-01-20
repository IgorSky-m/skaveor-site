package com.skachko.shop.order.service.entities.order.dto;

import com.skachko.shop.order.service.entities.order.api.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PlacedOrderResponse {
    private UUID id;
    private OrderStatus status;
}
