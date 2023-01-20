package com.skachko.shop.order.service.entities.order.dto;

import com.skachko.shop.order.service.entities.order.api.OrderStatus;
import com.skachko.shop.order.service.entities.payment.api.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private UUID orderId;
    private Instant orderDate;
    private OrderStatus orderStatus;
    private long amount;
    private List<ProductDetails> productDetails;
    private PaymentDetails paymentDetails;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetails {
        private UUID id;
        private String title;
        private String type;
        private String titlePicture;
        private long amount;
        private long quantity;
        //todo add quantity
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PaymentDetails{
        private UUID paymentId;
        private PaymentMode paymentMode;
        private String paymentStatus;
        private Instant paymentDate;
    }
}
