package com.skachko.shop.order.service.entities.order.dto;

import com.skachko.shop.order.service.entities.payment.api.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private UUID productId;
    private long totalAmount;
    private long quantity;
    private PaymentMode paymentMode;
}
