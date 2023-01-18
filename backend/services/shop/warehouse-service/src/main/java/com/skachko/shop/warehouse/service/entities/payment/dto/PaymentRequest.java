package com.skachko.shop.warehouse.service.entities.payment.dto;

import com.skachko.shop.warehouse.service.entities.payment.api.PaymentMode;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private UUID orderId;
    private long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;

}
