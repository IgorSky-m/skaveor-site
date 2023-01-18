package com.skachko.shop.payment.service.entities.payment.validator.api;

import com.skachko.shop.payment.service.entities.payment.dto.PaymentRequest;

public interface IPaymentValidator {
    void validatePaymentDetails(PaymentRequest request);
}
