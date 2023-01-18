package com.skachko.shop.order.service.entities.order.dto;

import com.skachko.shop.order.service.entities.payment.api.PaymentMode;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private List<ProductOrderRequest> products;
    private PaymentRequestDetails paymentDetails;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentRequestDetails {
        private PaymentMode paymentMode;
        private String cardNumber;
        private String cardName;
        private String expireDate;
        private Address billingAddress;
        private String email;
        private String phone;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {
        private String addressLineOne;
        private String addressLineTwo;
        private String apt;
        private String city;
        private String state;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOrderRequest {
        private UUID id;
        private long quantity;
    }
}
