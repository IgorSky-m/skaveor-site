package com.skachko.shop.payment.service.entities.payment.dto;

import com.skachko.shop.payment.service.entities.payment.api.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private UUID orderId;
    private long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;

}
