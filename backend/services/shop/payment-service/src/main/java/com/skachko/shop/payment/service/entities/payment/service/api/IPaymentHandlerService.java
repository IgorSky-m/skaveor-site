package com.skachko.shop.payment.service.entities.payment.service.api;

import com.skachko.shop.payment.service.entities.payment.dto.PaymentRequest;
import com.skachko.shop.payment.service.entities.payment.dto.PaymentResponse;

import java.util.UUID;


public interface IPaymentHandlerService {
    UUID doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(UUID orderId);
}
