package com.skachko.shop.payment.service.entities.payment.controller;

import com.skachko.shop.payment.service.entities.payment.dto.PaymentRequest;
import com.skachko.shop.payment.service.entities.payment.dto.PaymentResponse;
import com.skachko.shop.payment.service.entities.payment.service.api.IPaymentHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {

    private final IPaymentHandlerService paymentService;

    @PostMapping
    public ResponseEntity<UUID> doPayment(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest),
                HttpStatus.OK
        );
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable UUID orderId) {
        return new ResponseEntity<>(
                paymentService.getPaymentDetailsByOrderId(orderId),
                HttpStatus.OK
        );
    }
}
