package com.skachko.shop.order.service.entities.payment.client.api;


import com.skachko.shop.order.service.entities.payment.dto.PaymentRequest;
import com.skachko.shop.order.service.entities.payment.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "PAYMENT-SERVICE", url = "http://localhost:9001/payment")
public interface IPaymentServiceClient {

    @PostMapping
    ResponseEntity<UUID> doPayment(@RequestBody PaymentRequest paymentRequest);

    @GetMapping("/order/{orderId}")
    ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable UUID orderId);
}