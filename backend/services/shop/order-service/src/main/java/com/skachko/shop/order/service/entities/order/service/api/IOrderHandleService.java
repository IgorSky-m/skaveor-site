package com.skachko.shop.order.service.entities.order.service.api;

import com.skachko.shop.order.service.entities.order.dto.OrderRequest;
import com.skachko.shop.order.service.entities.order.dto.OrderResponse;
import com.skachko.shop.order.service.entities.order.dto.PlacedOrderResponse;

import java.util.UUID;

public interface IOrderHandleService {

    PlacedOrderResponse placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(UUID orderId);
}
