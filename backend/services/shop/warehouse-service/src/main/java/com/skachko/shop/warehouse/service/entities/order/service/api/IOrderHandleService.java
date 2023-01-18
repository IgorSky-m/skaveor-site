package com.skachko.shop.warehouse.service.entities.order.service.api;

import com.skachko.shop.warehouse.service.entities.order.dto.OrderRequest;
import com.skachko.shop.warehouse.service.entities.order.dto.OrderResponse;

import java.util.UUID;

public interface IOrderHandleService {

    UUID placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(UUID orderId);
}
