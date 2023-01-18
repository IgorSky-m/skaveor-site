package com.skachko.shop.warehouse.service.entities.payment.client.api;

import com.skachko.shop.warehouse.service.entities.payment.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE", url = "http://localhost:8083/payment")
public interface IPaymentServiceClient {

    @PostMapping
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);
}
