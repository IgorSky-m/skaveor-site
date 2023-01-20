package com.skachko.shop.order.service.entities.order.validator.api;

import com.skachko.shop.order.service.entities.order.dto.OrderRequest;

public interface IOrderValidator {
    void validateRequest(OrderRequest request);
}
