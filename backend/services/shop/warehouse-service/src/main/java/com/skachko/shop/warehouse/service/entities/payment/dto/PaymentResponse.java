package com.skachko.shop.warehouse.service.entities.payment.dto;

import com.skachko.shop.warehouse.service.entities.payment.api.PaymentMode;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private UUID paymentId;
    private String status;
    private PaymentMode paymentMode;
    private long amount;
    private Instant paymentDate;
    private UUID orderId;
}
