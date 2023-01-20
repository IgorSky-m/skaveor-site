package com.skachko.shop.order.service.entities.order.dto;

import com.skachko.shop.order.service.entities.order.api.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;

    @ElementCollection(fetch = FetchType.EAGER)
    List<OrderProductDetails> products;

    private Instant orderDate;

    private long totalAmount;

    private OrderStatus status;


}
