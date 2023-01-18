package com.skachko.shop.payment.service.entities.payment.validator;

import com.skachko.shop.payment.service.entities.payment.dto.PaymentRequest;
import com.skachko.shop.payment.service.entities.payment.validator.api.IPaymentValidator;
import com.skachko.shop.payment.service.exceptions.PaymentDetailsException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Fake validator with random reject
 */
@Component
public class FakePaymentValidator implements IPaymentValidator {
    @Override
    public void validatePaymentDetails(PaymentRequest request) {
        if (request.getAmount() == 0) {
            return;
        }
        if (ThreadLocalRandom.current().nextInt(0, 10) < 3) {
            throw new PaymentDetailsException("Payment details rejected");
        }
    }
}
